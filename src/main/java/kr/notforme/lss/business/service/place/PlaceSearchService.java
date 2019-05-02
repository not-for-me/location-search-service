package kr.notforme.lss.business.service.place;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.notforme.lss.api.response.PlaceSearchResult;
import kr.notforme.lss.business.model.search.SearchLog;
import kr.notforme.lss.business.repository.place.PlaceSearchRepository;
import kr.notforme.lss.business.service.search.SearchLogWriterService;
import kr.notforme.lss.support.cache.CacheKeyResolver;
import lombok.extern.slf4j.Slf4j;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PlaceSearchService {
    private PlaceSearchCacheService cacheService;
    private PlaceSearchRepository placeSearchRepository;

    public PlaceSearchService(PlaceSearchCacheService cacheService,
                              PlaceSearchRepository placeSearchRepository) {
        this.cacheService = cacheService;
        this.placeSearchRepository = placeSearchRepository;
    }

    @SuppressWarnings("unchecked")
    public Mono<List<PlaceSearchResult>> search(String keyword, Pageable page) {
        return CacheMono
                .lookup(key -> cacheService.getCachedSearchResult(key), cacheKey(keyword, page))
                .onCacheMissResume(() -> placeSearchRepository.search(keyword, page))
                .andWriteWith(
                        (key, value) -> Mono
                                .fromRunnable(() -> cacheService.putCache(key, value)));
    }

    private String cacheKey(String keyword, Pageable page) {
        return CacheKeyResolver.getPlaceSearchKey(keyword, page);
    }
}
