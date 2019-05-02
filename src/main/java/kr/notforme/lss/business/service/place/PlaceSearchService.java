package kr.notforme.lss.business.service.place;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.notforme.lss.api.response.PlaceSearchResult;
import kr.notforme.lss.business.repository.place.PlaceSearchRepository;
import kr.notforme.lss.support.cache.CacheKeyResolver;
import kr.notforme.lss.support.cache.ReactiveCacheManager;
import lombok.extern.slf4j.Slf4j;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PlaceSearchService {
    private ReactiveCacheManager reactiveCacheManager;
    private PlaceSearchRepository placeSearchRepository;

    public PlaceSearchService(ReactiveCacheManager reactiveCacheManager,
                              PlaceSearchRepository placeSearchRepository) {
        this.reactiveCacheManager = reactiveCacheManager;
        this.placeSearchRepository = placeSearchRepository;
    }

    @SuppressWarnings("unchecked")
    public Mono<List<PlaceSearchResult>> search(String keyword, Pageable page) {
        return CacheMono
                .lookup(key -> reactiveCacheManager.getPlaceSearchResultCache(key), cacheKey(keyword, page))
                .onCacheMissResume(() -> placeSearchRepository.search(keyword, page))
                .andWriteWith((key, value) -> Mono
                        .fromRunnable(
                                () -> reactiveCacheManager.putPlaceSearchResultCache(key, value)));
    }

    private String cacheKey(String keyword, Pageable page) {
        return CacheKeyResolver.getPlaceSearchKey(keyword, page);
    }
}
