package kr.notforme.lss.business.repository.search;

import java.time.LocalDateTime;
import java.util.List;

import kr.notforme.lss.business.model.search.SearchLog;
import kr.notforme.lss.business.model.search.TopSearchKeyword;

public interface SearchLogRepository {
    SearchLog writeLog(SearchLog log);

    List<TopSearchKeyword> findTopSearchKeyword(LocalDateTime minDate, int page, int size);
}
