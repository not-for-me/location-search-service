package kr.notforme.lss.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.notforme.lss.api.response.ApiResponse;
import kr.notforme.lss.api.response.SearchKeywordRanking;
import kr.notforme.lss.business.service.search.TopSearchKeywordService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/keywords")
public class SearchKeywordRankingController {
    private TopSearchKeywordService topSearchKeywordService;

    public SearchKeywordRankingController(
            TopSearchKeywordService topSearchKeywordService) {
        this.topSearchKeywordService = topSearchKeywordService;
    }

    @GetMapping("/ranking")
    public Mono<ApiResponse<List<SearchKeywordRanking>>> getSearchKeywordRanking() {
        return Mono.just(ApiResponse.of(topSearchKeywordService.getTopSearchKeywordRankings()));
    }
}
