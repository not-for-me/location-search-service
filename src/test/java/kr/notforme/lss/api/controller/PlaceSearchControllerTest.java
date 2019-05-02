package kr.notforme.lss.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import kr.notforme.lss.business.service.place.PlaceSearchService;
import kr.notforme.lss.business.service.search.SearchLogWriterService;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(PlaceSearchController.class)
public class PlaceSearchControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PlaceSearchService placeSearchService;
    @MockBean
    private SearchLogWriterService searchLogWriterService;

    @Test
    public void testSearch_given_no_secured_session_then_return_401() {
        // When
        webTestClient.get()
                     .uri("/places/search")
                     .exchange()
                     .expectStatus()
                     .isUnauthorized();

        // Then
        then(placeSearchService).should(never()).search(anyString(), any());
        then(searchLogWriterService).should(never()).writeSearchLog(anyString());
    }

    @Test
    @WithMockUser
    public void testSearch_given_no_keyword_then_return_400() {
        // When
        webTestClient.get()
                     .uri(UriComponentsBuilder.fromPath("/places/search").build().toUri())
                     .exchange()
                     .expectStatus()
                     .isBadRequest();

        // Then
        then(placeSearchService).should(never()).search(anyString(), any());
        then(searchLogWriterService).should(never()).writeSearchLog(anyString());
    }

    @Test
    @WithMockUser
    public void testSearch_given_empty_keyword_then_return_400() {
        // When
        webTestClient.get()
                     .uri(UriComponentsBuilder.fromPath("/places/search")
                                              .queryParam("keyword").build().toUri())
                     .exchange()
                     .expectStatus()
                     .isBadRequest();

        // Then
        then(placeSearchService).should(never()).search(anyString(), any());
        then(searchLogWriterService).should(never()).writeSearchLog(anyString());
    }

    @Test
    @WithMockUser
    public void testSearch_given_keyword_then_return_search_result() {
        // Given
        String keyword = "test";
        Pageable page = PageRequest.of(1, 10);

        given(placeSearchService.search(keyword, page)).willReturn(Mono.just(new ArrayList<>()));

        // When
        webTestClient.get()
                     .uri(UriComponentsBuilder.fromPath("/places/search")
                                                 .queryParam("keyword", keyword)
                                                 .queryParam("page", page.getPageNumber())
                                                 .queryParam("size", page.getPageSize())
                                                 .build().toUri())
                     .exchange()
                     .expectStatus()
                     .isOk();

        // Then
        then(placeSearchService).should().search(keyword, page);
        then(searchLogWriterService).should().writeSearchLog(keyword);
    }
}