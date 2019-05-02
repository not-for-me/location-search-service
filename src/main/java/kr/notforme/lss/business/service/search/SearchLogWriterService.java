package kr.notforme.lss.business.service.search;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import kr.notforme.lss.business.model.search.SearchLog;
import kr.notforme.lss.business.repository.search.SearchLogRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchLogWriterService {
    private final SearchLogRepository searchLogRepository;

    public SearchLogWriterService(
            SearchLogRepository searchLogRepository) {
        this.searchLogRepository = searchLogRepository;
    }

    @Async("searchLogWriteExecutor")
    public void writeSearchLog(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            log.debug("invalid keyword: {}", keyword);
            return;
        }

        log.debug("write a log: {}", keyword);

        searchLogRepository.writeLog(new SearchLog(keyword));
    }
}
