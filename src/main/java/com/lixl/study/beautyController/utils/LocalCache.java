package com.lixl.study.beautyController.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class LocalCache {

    private static final String DEFAULT_TYPE = "default";

    private static final int DEFAULT_DURATION = 5;

    private static final TimeUnit DEFAULT_UNIT = TimeUnit.MINUTES;

    private static final ConcurrentHashMap<String, LocalCache> map = new ConcurrentHashMap<>();

    private Cache cache;

    private LocalCache(Cache cache) {
        this.cache = cache;
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        Object o = cache.getIfPresent(key);
        return o;
    }

    public static class Builder {

        private String type;

        private Integer duration;

        private TimeUnit unit;

        public Builder() {
            this.type = DEFAULT_TYPE;
            this.duration = DEFAULT_DURATION;
            this.unit = DEFAULT_UNIT;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder expire(int duration, TimeUnit unit) {
            this.duration = duration;
            this.unit = unit;
            return this;
        }

        public LocalCache build() {
            LocalCache localCache = LocalCache.map.get(this.type);
            if (localCache != null) {
                return localCache;
            }
            Cache<String, Object> cache = Caffeine.newBuilder().expireAfterWrite(this.duration, this.unit).build();
            localCache = new LocalCache(cache);
            LocalCache.map.put(this.type, localCache);
            return localCache;
        }

    }

}
