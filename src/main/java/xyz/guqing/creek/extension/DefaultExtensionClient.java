package xyz.guqing.creek.extension;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.guqing.creek.extension.store.ExtensionStore;
import xyz.guqing.creek.extension.store.ExtensionStoreClient;

/**
 * DefaultExtensionClient is default implementation of ExtensionClient.
 *
 * @author johnniang
 */
@Service
public class DefaultExtensionClient implements ExtensionClient {

    private final ExtensionStoreClient storeClient;
    private final ExtensionConverter converter;

    public DefaultExtensionClient(ExtensionStoreClient storeClient, ExtensionConverter converter) {
        this.storeClient = storeClient;
        this.converter = converter;
    }

    @Override
    public <E extends Extension> List<E> list(Class<E> type, Predicate<E> predicate,
        Comparator<E> comparator) {
        var scheme = Schemes.INSTANCE.get(type);
        var storeNamePrefix = ExtensionUtil.buildStoreNamePrefix(scheme);

        var storesStream = storeClient.listByNamePrefix(storeNamePrefix).stream()
            .map(extensionStore -> converter.convertFrom(type, extensionStore));
        if (predicate != null) {
            storesStream = storesStream.filter(predicate);
        }
        if (comparator != null) {
            storesStream = storesStream.sorted(comparator);
        }
        return storesStream.toList();
    }

    @Override
    public <E extends Extension> Page<E> page(Class<E> type, Predicate<E> predicate,
        Comparator<E> comparators, int page, int size) {
        var pageable = PageRequest.of(page, size);
        var all = list(type, predicate, comparators);
        var total = all.size();
        var content =
            all.stream().limit(pageable.getPageSize()).skip(pageable.getOffset()).toList();
        return PageableExecutionUtils.getPage(content, pageable, () -> total);
    }

    @Override
    public <E extends Extension> Optional<E> fetch(Class<E> type, String name) {
        var scheme = Schemes.INSTANCE.get(type);

        var storeName = ExtensionUtil.buildStoreName(scheme, name);
        return storeClient.fetchByName(storeName)
            .map(extensionStore -> converter.convertFrom(type, extensionStore));
    }

    @Override
    public <E extends Extension> void create(E extension) {
        extension.getMetadata().setCreationTimestamp(Instant.now());
        var extensionStore = converter.convertTo(extension);
        storeClient.create(extensionStore.getName(), extensionStore.getData());
    }

    @Override
    public <E extends Extension> void update(E extension) {
        var extensionStore = converter.convertTo(extension);
        Assert.notNull(extension.getMetadata().getVersion(),
            "Extension version must not be null when updating");
        storeClient.update(extensionStore.getName(), extensionStore.getVersion(),
            extensionStore.getData());
    }

    @Override
    public <E extends Extension> void delete(E extension) {
        ExtensionStore extensionStore = converter.convertTo(extension);
        storeClient.delete(extensionStore.getName(), extensionStore.getVersion());
    }

}
