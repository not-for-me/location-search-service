package kr.notforme.lss.business.service.search;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.notforme.lss.api.response.SearchKeywordRanking;
import kr.notforme.lss.business.model.search.TopSearchKeyword;
import kr.notforme.lss.business.repository.search.TopSearchKeywordRepository;

@Service
public class TopSearchKeywordService {
    private TopSearchKeywordRepository topSearchKeywordRepository;

    public TopSearchKeywordService(
            TopSearchKeywordRepository topSearchKeywordRepository) {
        this.topSearchKeywordRepository = topSearchKeywordRepository;
    }

    public List<SearchKeywordRanking> getTopSearchKeywordRankings() {
        List<TopSearchKeyword> keywords =
                topSearchKeywordRepository.findTop10SearchKeywords();
        AtomicInteger rank = new AtomicInteger(0);

       return keywords.stream()
                .map(k -> new SearchKeywordRanking(rank.incrementAndGet(), k.getKeyword(), k.getCount()))
                .collect(Collectors.toList());
    }
}
