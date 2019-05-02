package kr.notforme.lss.business.service.place;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.notforme.lss.api.response.PlaceSearchResult;
import kr.notforme.lss.business.repository.place.PlaceSearchRepository;
import kr.notforme.lss.support.cache.CacheKeyResolver;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;

@Service
public class PlaceSearchService {
    private PlaceSearchRepository placeSearchRepository;
    private CacheManager manager;

    @Autowired
    @Qualifier("kakaoPlaceSearchRepository")
    public void setPlaceSearchRepository(PlaceSearchRepository placeSearchRepository) {
        this.placeSearchRepository = placeSearchRepository;
    }

    @Autowired
    public void setManager(CacheManager manager) {
        this.manager = manager;
    }

    @SuppressWarnings("unchecked")
    public Mono<List<PlaceSearchResult>> search(String keyword, Pageable page) {
        return CacheMono.lookup(key -> (Mono) Optional.ofNullable(manager.getCache("placeSearchResult"))
                                                      .map(m -> m.get(key))
                                                      .map(ValueWrapper::get)
                                                      .map(Mono::just)
                                                      .orElse(Mono.empty())
                , CacheKeyResolver.getPlaceSearchKey(keyword, page))
                        .onCacheMissResume(() -> placeSearchRepository.search(keyword, page))
                        .andWriteWith((key, value) -> Mono.fromRunnable(
                                () -> Objects.requireNonNull(manager.getCache("placeSearchResult"))
                                             .put(key, value)));
    }
}
