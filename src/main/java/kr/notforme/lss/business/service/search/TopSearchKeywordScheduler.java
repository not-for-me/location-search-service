package kr.notforme.lss.business.service.search;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.notforme.lss.business.model.search.TopSearchKeyword;
import kr.notforme.lss.business.repository.search.SearchLogRepository;
import kr.notforme.lss.business.repository.search.TopSearchKeywordRepository;
import kr.notforme.lss.support.cache.CacheKeyResolver;
import kr.notforme.lss.support.cache.ReactiveCacheManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TopSearchKeywordScheduler {
    // It would be adjust according to log data volume
    private static final int SEARCH_DURATION_IN_DAYS = 30;
    private SearchLogRepository searchLogRepository;
    private TopSearchKeywordRepository topSearchKeywordRepository;
    private ReactiveCacheManager reactiveCacheManager;

    public TopSearchKeywordScheduler(
            SearchLogRepository searchLogRepository,
            TopSearchKeywordRepository topSearchKeywordRepository,
            ReactiveCacheManager reactiveCacheManager) {
        this.searchLogRepository = searchLogRepository;
        this.topSearchKeywordRepository = topSearchKeywordRepository;
        this.reactiveCacheManager = reactiveCacheManager;
    }

    // It would be adjust according to log data volume
    @Scheduled(fixedDelay = 5_000) // 1min
    @Transactional
    public void refreshTop10SearchKeyword() {
        log.debug("Start to fetch top search keywords");

        topSearchKeywordRepository.clearData();

        LocalDateTime minDate = LocalDateTime.now().minusMinutes(SEARCH_DURATION_IN_DAYS);
        List<TopSearchKeyword> topKeywords =
                searchLogRepository.findTopSearchKeyword(minDate, 0, 10);

        topSearchKeywordRepository.saveAll(topKeywords);

        log.debug("Finish to fetch top search keywords");
        this.reactiveCacheManager.evictSearchKeywordRankingCache(CacheKeyResolver.getSearchRankingKey());
    }
}
