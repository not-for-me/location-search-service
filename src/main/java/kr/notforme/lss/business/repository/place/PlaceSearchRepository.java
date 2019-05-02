package kr.notforme.lss.business.repository.place;

import java.util.List;

import org.springframework.data.domain.Pageable;

import kr.notforme.lss.api.response.PlaceSearchResult;
import reactor.core.publisher.Mono;

public interface PlaceSearchRepository {
    Mono<List<PlaceSearchResult>> search(String keyword, Pageable page);
}
