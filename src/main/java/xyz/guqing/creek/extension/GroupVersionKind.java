package xyz.guqing.creek.extension;

import org.springframework.util.Assert;

/**
 * GroupVersionKind contains group, version and kind name of an Extension.
 *
 * @param group is group name of Extension.
 * @param version is version name of Extension.
 * @param kind is kind name of Extension.
 * @author johnniang
 */
public record GroupVersionKind(String group, String version, String kind) {

    public GroupVersionKind {
        Assert.hasText(version, "Version must not be blank");
        Assert.hasText(kind, "Kind must not be blank");
    }

    /**
     * Gets group and version name of Extension.
     *
     * @return group and version name of Extension.
     */
    public GroupVersion groupVersion() {
        return new GroupVersion(group, version);
    }

    /**
     * Composes GroupVersionKind from API version and kind name.
     *
     * @param apiVersion is API version. Like "core.halo.run/v1alpha1"
     * @param kind is kind name of Extension.
     * @return GroupVersionKind of an Extension.
     */
    public static GroupVersionKind fromAPIVersionAndKind(String apiVersion, String kind) {
        Assert.hasText(kind, "Kind must not be blank");

        var gv = GroupVersion.parseAPIVersion(apiVersion);
        return new GroupVersionKind(gv.group(), gv.version(), kind);
    }
}
