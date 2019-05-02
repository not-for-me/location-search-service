package kr.notforme.lss.business.service.search;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import kr.notforme.lss.business.repository.search.SearchLogRepository;
import kr.notforme.lss.business.repository.search.TopSearchKeywordRepository;
import kr.notforme.lss.support.cache.ReactiveCacheManager;

@RunWith(MockitoJUnitRunner.class)
public class TopSearchKeywordSchedulerTest {
    @InjectMocks
    private TopSearchKeywordScheduler sut;

    @Mock
    private SearchLogRepository searchLogRepository;
    @Mock
    private TopSearchKeywordRepository topSearchKeywordRepository;
    @Mock
    private ReactiveCacheManager reactiveCacheManager;

    @Test
    public void testRefreshTop10SearchKeyword() {
        // Given
        given(searchLogRepository.findTopSearchKeyword(any(), anyInt(), anyInt())).willReturn(new ArrayList<>());

        // When
        sut.refreshTop10SearchKeyword();

        // Then
        then(topSearchKeywordRepository).should().clearData();
        then(searchLogRepository).should().findTopSearchKeyword(any(), anyInt(), anyInt());
        then(topSearchKeywordRepository).should().saveAll(anyCollection());
        then(reactiveCacheManager).should().evictSearchKeywordRankingCache(any());
    }
}