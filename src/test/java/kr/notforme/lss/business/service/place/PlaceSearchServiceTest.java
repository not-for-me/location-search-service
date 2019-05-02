package kr.notforme.lss.business.service.place;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import kr.notforme.lss.api.response.PlaceSearchResult;
import kr.notforme.lss.business.repository.place.PlaceSearchRepository;
import kr.notforme.lss.support.cache.CacheKeyResolver;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class PlaceSearchServiceTest {
    @InjectMocks
    private PlaceSearchService sut;

    @Mock
    private PlaceSearchCacheService placeSearchCacheService;
    @Mock
    private PlaceSearchRepository placeSearchRepository;

    @Test
    public void search_given_keyword_no_cache_then_fetch_repository() {
        // Given
        final String keyword = "search";
        final Pageable page = PageRequest.of(1, 10);
        String key = CacheKeyResolver.getPlaceSearchKey(keyword, page);

        given(placeSearchCacheService.getCachedSearchResult(key)).willReturn(Mono.empty());
        given(placeSearchRepository.search(keyword, page)).willReturn(Mono.just(new ArrayList<>()));

        // When
        Mono<List<PlaceSearchResult>> actualMono = sut.search(keyword, page);

        // Then
        StepVerifier.create(actualMono)
                    .expectNext(new ArrayList<>())
                    .verifyComplete();
        then(placeSearchCacheService).should().putCache(eq(key), any());
        then(placeSearchRepository).should().search(keyword, page);
    }

    @Test
    public void search_given_keyword_has_cache_then_fetch_repository() {
        // Given
        final String keyword = "search";
        final Pageable page = PageRequest.of(1, 10);

        // When
        sut.search(keyword, page);

        // Then
        then(placeSearchRepository).should(never()).search(any(), any());
        then(placeSearchCacheService).should(never()).putCache(any(), any());
    }
}