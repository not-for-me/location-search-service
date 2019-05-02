package kr.notforme.lss.business.service.search;

import static org.assertj.core.api.Assertions.assertThat;
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

import kr.notforme.lss.api.response.SearchKeywordRanking;
import kr.notforme.lss.business.model.search.TopSearchKeyword;
import kr.notforme.lss.business.repository.search.TopSearchKeywordRepository;
import kr.notforme.lss.support.cache.CacheKeyResolver;
import kr.notforme.lss.support.cache.ReactiveCacheManager;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class TopSearchKeywordServiceTest {
    @InjectMocks
    private TopSearchKeywordService sut;

    @Mock
    private ReactiveCacheManager reactiveCacheManager;
    @Mock
    private TopSearchKeywordRepository topSearchKeywordRepository;

    @Test
    public void getTopSearchKeywordRankings_given_no_cache_then_fetch_from_db() {
        // Given
        String key = CacheKeyResolver.getSearchRankingKey();
        List<TopSearchKeyword> dbData = new ArrayList<>();
        dbData.add(new TopSearchKeyword("k1", 100L));
        dbData.add(new TopSearchKeyword("k2", 90L));
        dbData.add(new TopSearchKeyword("k3", 80L));

        given(reactiveCacheManager.getSearchKeywordRankingCache(key)).willReturn(Mono.empty());
        given(topSearchKeywordRepository.findTop10SearchKeywords()).willReturn(dbData);

        // When
        Mono<List<SearchKeywordRanking>> actualMono = sut.getTopSearchKeywordRankings();

        // Then
        assertThat(actualMono).isNotNull();
        StepVerifier.create(actualMono)
                    .expectNextMatches(s -> s.size() == dbData.size())
                    .verifyComplete();

        then(reactiveCacheManager).should().putSearchKeywordRankingCache(eq(key), any());
        then(topSearchKeywordRepository).should().findTop10SearchKeywords();
    }

    @Test
    public void getTopSearchKeywordRankings_given_cache_then_return_ranking_list_from_cache() {
        // Given
        String key = CacheKeyResolver.getSearchRankingKey();
        List<SearchKeywordRanking> cacheData = new ArrayList<>();
         cacheData.add(new SearchKeywordRanking(1, "k1", 100L));
         cacheData.add(new SearchKeywordRanking(2, "k2", 90L));
         cacheData.add(new SearchKeywordRanking(3, "k3", 80L));

//        given(reactiveCacheManager.getSearchKeywordRankingCache(key)).willReturn(Mono.just(cacheData));

        // When
        Mono<List<SearchKeywordRanking>> actualMono = sut.getTopSearchKeywordRankings();

        // Then
        assertThat(actualMono).isNotNull();
        then(reactiveCacheManager).should(never()).putSearchKeywordRankingCache(eq(key), any());
        then(topSearchKeywordRepository).should(never()).findTop10SearchKeywords();
    }
}