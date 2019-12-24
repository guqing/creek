package xyz.guqing.app.config;

import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * @author guqing
 * @date 2019-12-24 13:20
 */
public class MyAuthStateCache implements AuthStateCache {
    private final Cache cache;
    public MyAuthStateCache(Cache cache) {
        this.cache = cache;
    }

    /**
     * 存入缓存
     * @param key   缓存key
     * @param value 缓存内容
     */
    @Override
    public void cache(String key, String value) {
        cache.put(key, value);
    }

    /**
     * 存入缓存
     *
     * @param key     缓存key
     * @param value   缓存内容
     * @param timeout 指定缓存过期时间（毫秒）
     */
    @Override
    public void cache(String key, String value, long timeout) {
        cache.put(key, value);
    }

    /**
     * 获取缓存内容
     *
     * @param key 缓存key
     * @return 缓存内容
     */
    @Override
    public String get(String key) {
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if(valueWrapper!= null) {
            return valueWrapper.toString();
        }
        return null;
    }

    /**
     * 是否存在key，如果对应key的value值已过期，也返回false
     *
     * @param key 缓存key
     * @return true：存在key，并且value没过期；false：key不存在或者已过期
     */
    @Override
    public boolean containsKey(String key) {
        return cache.get(key) != null;
    }
}
