package kr.notforme.lss.business.repository.search;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import kr.notforme.lss.business.model.search.SearchLog;
import kr.notforme.lss.business.model.search.TopSearchKeyword;

@Component
public class SearchLogDbRepositoryAdaptor implements SearchLogRepository {
    private SearchLogDbRepository searchLogDbRepository;

    public SearchLogDbRepositoryAdaptor(
            SearchLogDbRepository searchLogDbRepository) {
        this.searchLogDbRepository = searchLogDbRepository;
    }

    @Override
    public SearchLog writeLog(SearchLog log) {
        return searchLogDbRepository.save(log);
    }

    @Override
    public List<TopSearchKeyword> findTopSearchKeyword(LocalDateTime minDate, int page, int size) {
        return searchLogDbRepository.findTopSearchKeyword(minDate, PageRequest.of(page, size));
    }
}
