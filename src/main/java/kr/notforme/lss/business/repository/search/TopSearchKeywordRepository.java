package kr.notforme.lss.business.repository.search;

import java.util.Collection;
import java.util.List;

import kr.notforme.lss.business.model.search.TopSearchKeyword;

public interface TopSearchKeywordRepository {
    TopSearchKeyword save(TopSearchKeyword topSearchKeyword);

    void clearData();

    List<TopSearchKeyword> saveAll(Collection<TopSearchKeyword> topSearchKeywords);

    List<TopSearchKeyword> findTop10SearchKeywords();
}
