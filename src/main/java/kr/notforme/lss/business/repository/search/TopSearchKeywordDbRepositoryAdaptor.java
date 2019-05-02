package kr.notforme.lss.business.repository.search;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import kr.notforme.lss.business.model.search.TopSearchKeyword;

@Component
public class TopSearchKeywordDbRepositoryAdaptor implements TopSearchKeywordRepository {
    private TopSearchKeywordDbRepository topSearchKeywordDbRepository;

    public TopSearchKeywordDbRepositoryAdaptor(
            TopSearchKeywordDbRepository topSearchKeywordDbRepository) {
        this.topSearchKeywordDbRepository = topSearchKeywordDbRepository;
    }

    @Override
    public TopSearchKeyword save(TopSearchKeyword topSearchKeyword) {
        return topSearchKeywordDbRepository.save(topSearchKeyword);
    }

    @Override
    public void clearData() {
       topSearchKeywordDbRepository.deleteAll();
    }

    @Override
    public List<TopSearchKeyword> saveAll(Collection<TopSearchKeyword> topSearchKeywords) {
        return topSearchKeywordDbRepository.saveAll(topSearchKeywords);
    }

    @Override
    public List<TopSearchKeyword> findTop10SearchKeywords() {
        return topSearchKeywordDbRepository.findTop10ByOrderByCountDesc();
    }
}
