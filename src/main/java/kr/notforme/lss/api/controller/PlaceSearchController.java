package kr.notforme.lss.api.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.notforme.lss.api.response.ApiResponse;
import kr.notforme.lss.api.response.PlaceSearchResult;
import kr.notforme.lss.business.service.place.PlaceSearchService;
import kr.notforme.lss.support.exceptions.LssApiException;
import kr.notforme.lss.support.exceptions.ResourceAccessException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places")
public class PlaceSearchController {
    private final PlaceSearchService placeSearchService;

    public PlaceSearchController(PlaceSearchService placeSearchService) {
        this.placeSearchService = placeSearchService;
    }

    @GetMapping("/search")
    public Mono<ApiResponse<List<PlaceSearchResult>>> search(@RequestParam(required = false) String keyword,
                                                             @RequestParam(required = false, defaultValue = "1") int page,
                                                             @RequestParam(required = false, defaultValue = "15") int size) {
        if (StringUtils.isBlank(keyword)) {
            return Mono.error(new LssApiException("not found keyword", HttpStatus.BAD_REQUEST));
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return placeSearchService.search(keyword, pageRequest)
                                 .map(ApiResponse::of)
                                 .onErrorMap(ResourceAccessException.class,
                                             e -> new LssApiException("unavailable to search resources", HttpStatus.SERVICE_UNAVAILABLE));
    }
}
