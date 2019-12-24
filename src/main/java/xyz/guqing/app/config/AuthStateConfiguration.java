package xyz.guqing.app.config;

import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义社交登录缓存装配
 * @author guqing
 * @date 2019-12-24 13:36
 */
@Configuration
public class AuthStateConfiguration {
    private static final String CACHE_NAMES = "userService";
    private final CacheManager cacheManager;

    @Autowired
    public AuthStateConfiguration(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Bean
    public AuthStateCache authStateCache() {
        Cache cache = cacheManager.getCache(CACHE_NAMES);
        return new MyAuthStateCache(cache);
    }
}
