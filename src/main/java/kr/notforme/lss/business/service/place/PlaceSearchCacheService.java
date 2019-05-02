package kr.notforme.lss.business.service.place;

import java.util.Objects;
import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class PlaceSearchCacheService {
    private static final String CACHE_NAME = "placeSearchResult";
    private CacheManager cacheManager;

    public PlaceSearchCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Mono getCachedSearchResult(String key) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        ValueWrapper wrapper = Objects.requireNonNull(cache).get(key);

        return Optional.ofNullable(wrapper)
                       .map(ValueWrapper::get)
                       .map(Mono::just)
                       .orElse(Mono.empty());
    }

    public void putCache(Object key, Object value) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        cache.put(key, value);
    }
}
