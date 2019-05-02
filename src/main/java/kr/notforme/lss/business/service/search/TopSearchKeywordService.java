package kr.notforme.lss.business.service.search;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.notforme.lss.api.response.SearchKeywordRanking;
import kr.notforme.lss.business.model.search.TopSearchKeyword;
import kr.notforme.lss.business.repository.search.TopSearchKeywordRepository;
import kr.notforme.lss.support.cache.CacheKeyResolver;
import kr.notforme.lss.support.cache.ReactiveCacheManager;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;

@Service
public class TopSearchKeywordService {
    private TopSearchKeywordRepository topSearchKeywordRepository;
    private ReactiveCacheManager reactiveCacheManager;

    public TopSearchKeywordService(
            TopSearchKeywordRepository topSearchKeywordRepository,
            ReactiveCacheManager reactiveCacheManager) {
        this.topSearchKeywordRepository = topSearchKeywordRepository;
        this.reactiveCacheManager = reactiveCacheManager;
    }

    @SuppressWarnings("unchecked")
    public Mono<List<SearchKeywordRanking>> getTopSearchKeywordRankings() {
        return CacheMono
                .lookup(key -> reactiveCacheManager.getSearchKeywordRankingCache(key), cacheKey())
                .onCacheMissResume(this::fetchSearchKeywordRankings)
                .andWriteWith((key, value) -> Mono.fromRunnable(
                        () -> reactiveCacheManager.putSearchKeywordRankingCache(key, value)));
    }

    private String cacheKey() {
        return CacheKeyResolver.getSearchRankingKey();
    }

    private Mono<List<SearchKeywordRanking>> fetchSearchKeywordRankings() {
        List<TopSearchKeyword> keywords = topSearchKeywordRepository.findTop10SearchKeywords();

        AtomicInteger rank = new AtomicInteger(0);
        List<SearchKeywordRanking> rankings = keywords
                .stream()
                .map(k -> new SearchKeywordRanking(rank.incrementAndGet(), k.getKeyword(), k.getCount()))
                .collect(Collectors.toList());

        return Mono.just(rankings);
    }
}
