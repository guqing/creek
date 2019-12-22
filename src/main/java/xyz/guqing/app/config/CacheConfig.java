package xyz.guqing.app.config;

import com.github.benmanes.caffeine.cache.CacheWriter;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * 缓存配置类
 *
 * @author guqin
 * @date 2019-08-24 21:34
 */
@Configuration
public class CacheConfig {
	/**
	 * 配置CacheManager
	 */
	@Bean(name = "caffeine")
	public CacheManager cacheManagerWithCaffeine() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		Caffeine caffeine = Caffeine.newBuilder()
				//cache的初始容量值
				.initialCapacity(100)
				//maximumSize用来控制cache的最大缓存数量，maximumSize和maximumWeight(最大权重)不可以同时使用，
				.maximumSize(1000)
				//最后一次写入或者访问后过多久过期
				.expireAfterAccess(500, TimeUnit.SECONDS)
				//缓存写入/删除监控
                .writer(new CacheWriter<Object, Object>() {
					@Override
					public void write(@NonNull Object key, @NonNull Object value) { //此方法是同步阻塞的
						System.out.println("--缓存写入--:key=" + key + ", value=" + value);
					}
					@Override
					public void delete(@NonNull Object key, Object value,@NonNull RemovalCause cause) {
						System.out.println("--缓存删除--:key=" + key);
					}
				});
		cacheManager.setCaffeine(caffeine);
		//是否允许值为空
		cacheManager.setAllowNullValues(false);
		return cacheManager;
	}

}
