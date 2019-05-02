package kr.notforme.lss.business.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import kr.notforme.lss.api.response.SearchKeywordRanking;
import kr.notforme.lss.business.model.search.TopSearchKeyword;
import kr.notforme.lss.business.repository.search.TopSearchKeywordRepository;

@RunWith(MockitoJUnitRunner.class)
public class TopSearchKeywordServiceTest {
    @InjectMocks
    private TopSearchKeywordService sut;

    @Mock
    private TopSearchKeywordRepository topSearchKeywordRepository;

    @Test
    public void getTopSearchKeywordRankings_given_no_data_then_empty_ranking_list() {
        // Given
        given(topSearchKeywordRepository.findTop10SearchKeywords()).willReturn(new ArrayList<>());

        // When
        List<SearchKeywordRanking> actual = sut.getTopSearchKeywordRankings();

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual).isEmpty();
    }

    @Test
    public void getTopSearchKeywordRankings_given_data_then_return_ranking_list() {
        // Given
        List<TopSearchKeyword> dbData = new ArrayList<>();
        dbData.add(new TopSearchKeyword("k1", 100L));
        dbData.add(new TopSearchKeyword("k2", 90L));
        dbData.add(new TopSearchKeyword("k3", 80L));
        dbData.add(new TopSearchKeyword("k4", 70L));
        dbData.add(new TopSearchKeyword("k5", 60L));

        given(topSearchKeywordRepository.findTop10SearchKeywords()).willReturn(dbData);

        // When
        List<SearchKeywordRanking> actual = sut.getTopSearchKeywordRankings();

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(5);
        assertThat(actual.get(0).getRankPosition()).isEqualTo(1);
        assertThat(actual.get(1).getRankPosition()).isEqualTo(2);
        assertThat(actual.get(2).getRankPosition()).isEqualTo(3);
        assertThat(actual.get(3).getRankPosition()).isEqualTo(4);
        assertThat(actual.get(4).getRankPosition()).isEqualTo(5);
        assertThat(actual.get(0).getKeyword()).isEqualTo("k1");
        assertThat(actual.get(1).getKeyword()).isEqualTo("k2");
        assertThat(actual.get(2).getKeyword()).isEqualTo("k3");
        assertThat(actual.get(3).getKeyword()).isEqualTo("k4");
        assertThat(actual.get(4).getKeyword()).isEqualTo("k5");
        assertThat(actual.get(0).getCount()).isEqualTo(100L);
        assertThat(actual.get(1).getCount()).isEqualTo(90L);
        assertThat(actual.get(2).getCount()).isEqualTo(80L);
        assertThat(actual.get(3).getCount()).isEqualTo(70L);
        assertThat(actual.get(4).getCount()).isEqualTo(60L);
    }
}