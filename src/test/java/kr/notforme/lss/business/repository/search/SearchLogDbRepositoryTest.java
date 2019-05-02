package kr.notforme.lss.business.repository.search;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.notforme.lss.business.model.search.SearchLog;
import kr.notforme.lss.business.model.search.TopSearchKeyword;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SearchLogDbRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SearchLogDbRepository sut;

    @Test
    public void testInsertAndSelect() {
        // Given
        SearchLog log = new SearchLog("keyword1");

        // When
        SearchLog actual = sut.save(log);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getSeq()).isPositive();
        assertThat(actual.getKeyword()).isEqualTo("keyword1");
        assertThat(actual.getCreatedTime()).isBefore(LocalDateTime.now());

        // When
        Optional<SearchLog> actual2 = sut.findById(actual.getSeq());

        // Then
        assertThat(actual2.isPresent()).isTrue();
        assertThat(actual2.get()).isEqualTo(actual);
    }

    @Test
    public void findTopSearchKeyword() {
        // Given
        int top1Count = 15;
        IntStream.rangeClosed(1, top1Count)
                 .forEach((i) -> entityManager.persist(new SearchLog("keyword1")));

        int top2Count = 8;
        IntStream.rangeClosed(1, top2Count)
                 .forEach((i) -> entityManager.persist(new SearchLog("keyword2")));

        int top3Count = 5;
        IntStream.rangeClosed(1, top3Count)
                 .forEach((i) -> entityManager.persist(new SearchLog("keyword3")));

        // When
        List<TopSearchKeyword> actual =
                sut.findTopSearchKeyword(LocalDateTime.now().minusMinutes(10), PageRequest.of(0, 5));

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(3);
        assertThat(actual.get(0).getKeyword()).isEqualTo("keyword1");
        assertThat(actual.get(0).getCount()).isEqualTo(top1Count);
        assertThat(actual.get(1).getKeyword()).isEqualTo("keyword2");
        assertThat(actual.get(1).getCount()).isEqualTo(top2Count);
        assertThat(actual.get(2).getKeyword()).isEqualTo("keyword3");
        assertThat(actual.get(2).getCount()).isEqualTo(top3Count);

    }
}