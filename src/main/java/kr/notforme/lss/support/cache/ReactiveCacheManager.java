package kr.notforme.lss.support.cache;

import java.util.Objects;
import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class ReactiveCacheManager {
    private static final String PLACE_SEARCH_RESULT_CACHE_NAME = "placeSearchResult";
    private static final String SEARCH_KEYWORD_RANKING_CACHE_NAME = "searchKeywordRanking";
    private CacheManager cacheManager;

    public ReactiveCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Mono getPlaceSearchResultCache(String key) {
        Cache cache = cacheManager.getCache(PLACE_SEARCH_RESULT_CACHE_NAME);
        ValueWrapper wrapper = Objects.requireNonNull(cache).get(key);
        return convertToMono(wrapper);
    }

    public void putPlaceSearchResultCache(Object key, Object value) {
        Cache cache = cacheManager.getCache(PLACE_SEARCH_RESULT_CACHE_NAME);
        cache.put(key, value);
    }

    public Mono getSearchKeywordRankingCache(String key) {
        Cache cache = cacheManager.getCache(SEARCH_KEYWORD_RANKING_CACHE_NAME);
        ValueWrapper wrapper = Objects.requireNonNull(cache).get(key);
        return convertToMono(wrapper);
    }

    public void evictSearchKeywordRankingCache(String key) {
        Cache cache = cacheManager.getCache(SEARCH_KEYWORD_RANKING_CACHE_NAME);
        cache.evict(key);
    }

    public void putSearchKeywordRankingCache(Object key, Object value) {
        Cache cache = cacheManager.getCache(SEARCH_KEYWORD_RANKING_CACHE_NAME);
        cache.put(key, value);
    }

    private Mono convertToMono(ValueWrapper wrapper) {
        return Optional.ofNullable(wrapper)
                       .map(ValueWrapper::get)
                       .map(Mono::just)
                       .orElse(Mono.empty());
    }
}
