package kr.notforme.lss.business.repository.place;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import kr.notforme.lss.api.response.PlaceSearchResult;
import kr.notforme.lss.config.props.ExternalServiceProp;
import kr.notforme.lss.config.props.ExternalServiceProp.ExternalService;
import kr.notforme.lss.config.props.ExternalServiceProp.LocalPlaceSearch;
import kr.notforme.lss.support.exceptions.ResourceAccessException;
import kr.notforme.lss.support.http.WebClientFilters;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class KakaoPlaceSearchRepository implements PlaceSearchRepository {
    private final ExternalService kakaoApiInfo;

    public KakaoPlaceSearchRepository(ExternalServiceProp externalServiceProp) {
        this.kakaoApiInfo = Objects.requireNonNull(externalServiceProp.getLocalPlaceSearch(),
                               "required local place search info").getKakao();

        Objects.requireNonNull(kakaoApiInfo, "required kakao place search open api endpoint info");
    }

    public Mono<List<PlaceSearchResult>> search(String keyword, Pageable page) {
        return WebClient.builder()
                        .baseUrl(kakaoApiInfo.getHost())
                        .filter(WebClientFilters.requestLogFilter())
                        .filter(WebClientFilters.responseLogFilter())
                        .build()
                        .get()
                        .uri(uriBuilder -> uriBuilder
                                .path(kakaoApiInfo.getUri())
                                .queryParam("query", keyword)
                                .queryParam("page", page.getPageNumber())
                                .queryParam("size", page.getPageSize())
                                .build())
                        .header("Authorization", "KakaoAK " + kakaoApiInfo.getToken())
                        .retrieve()
                        .onStatus(HttpStatus::isError, clientResponse ->
                                Mono.error(new ResourceAccessException())
                        )
                        .bodyToMono(KakaoPlaceSearchResponse.class)
                        .map(KakaoPlaceSearchResponse::getDocuments)
                        .map(kakaoList -> kakaoList.stream().map(this::convert).collect(Collectors.toList()));
    }

    // visible for testing
    public PlaceSearchResult convert(KakaoPlaceSearchResult kakaoPlaceSearchResult) {
        PlaceSearchResult placeSearchResult = new PlaceSearchResult();

        placeSearchResult.setPlaceName(kakaoPlaceSearchResult.getPlaceName());
        placeSearchResult.setPlaceUrl(kakaoPlaceSearchResult.getPlaceUrl());
        placeSearchResult.setAddress(kakaoPlaceSearchResult.getAddressName());
        placeSearchResult.setRoadAddress(kakaoPlaceSearchResult.getRoadAddressName());
        placeSearchResult.setPhone(kakaoPlaceSearchResult.getPhone());

        return placeSearchResult;
    }

    @Data
    private static class KakaoPlaceSearchResponse {
        private List<KakaoPlaceSearchResult> documents;
    }

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class KakaoPlaceSearchResult {
        private String addressName;
        private String categoryGroupCode;
        private String categoryGroupName;
        private String categoryName;
        private String distance;
        private String id;
        private String phone;
        private String placeName;
        private String placeUrl;
        private String roadAddressName;
        private String x;
        private String y;
    }
}
