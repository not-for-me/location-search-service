package kr.notforme.lss.api.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import kr.notforme.lss.business.service.search.TopSearchKeywordService;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(SearchKeywordRankingController.class)
public class SearchKeywordRankingControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TopSearchKeywordService topSearchKeywordService;

    @Test
    @WithMockUser
    public void testSearchKeywordRankings() {
        // Given
        given(topSearchKeywordService.getTopSearchKeywordRankings()).willReturn(Mono.empty());

        // When
        webTestClient.get()
                     .uri(UriComponentsBuilder.fromPath("/keywords/ranking").build().toUri())
                     .exchange()
                     .expectStatus()
                     .isOk();

        // Then
        then(topSearchKeywordService).should().getTopSearchKeywordRankings();
    }
}