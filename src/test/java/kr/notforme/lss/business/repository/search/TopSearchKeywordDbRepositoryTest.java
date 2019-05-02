package kr.notforme.lss.business.repository.search;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.notforme.lss.business.model.search.TopSearchKeyword;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TopSearchKeywordDbRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TopSearchKeywordDbRepository sut;

    @Test
    public void testTopSearchKeywordBulkSave() {
        // Given
        List<TopSearchKeyword> topSearchKeywords = new ArrayList<>();
        topSearchKeywords.add(new TopSearchKeyword("k1", 1000L));
        topSearchKeywords.add(new TopSearchKeyword("k2", 200L));
        topSearchKeywords.add(new TopSearchKeyword("k3", 1900L));
        topSearchKeywords.add(new TopSearchKeyword("k4", 10L));

        // When
        List<TopSearchKeyword> actual = sut.saveAll(topSearchKeywords);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(4);
    }

    @Test
    public void testFindTopByOrderByCountDesc() {
        // Given
        entityManager.persist(new TopSearchKeyword("k1", 1L));
        entityManager.persist(new TopSearchKeyword("k3", 3L));
        entityManager.persist(new TopSearchKeyword("k8", 8L));
        entityManager.persist(new TopSearchKeyword("k5", 5L));
        entityManager.persist(new TopSearchKeyword("k6", 6L));
        entityManager.persist(new TopSearchKeyword("k4", 4L));
        entityManager.persist(new TopSearchKeyword("k10", 10L));
        entityManager.persist(new TopSearchKeyword("k8", 8L));
        entityManager.persist(new TopSearchKeyword("k9", 9L));
        entityManager.persist(new TopSearchKeyword("k7", 7L));
        entityManager.persist(new TopSearchKeyword("k4", 4L));

        // When
        List<TopSearchKeyword> actual = sut.findTop10ByOrderByCountDesc();

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(10);
        assertThat(actual.get(0).getKeyword()).isEqualTo("k10");
        assertThat(actual.get(0).getCount()).isEqualTo(10L);
        assertThat(actual.get(1).getKeyword()).isEqualTo("k9");
        assertThat(actual.get(1).getCount()).isEqualTo(9L);
        assertThat(actual.get(2).getKeyword()).isEqualTo("k8");
        assertThat(actual.get(2).getCount()).isEqualTo(8L);
        assertThat(actual.get(3).getKeyword()).isEqualTo("k8");
        assertThat(actual.get(3).getCount()).isEqualTo(8L);
        assertThat(actual.get(4).getKeyword()).isEqualTo("k7");
        assertThat(actual.get(4).getCount()).isEqualTo(7L);
        assertThat(actual.get(5).getKeyword()).isEqualTo("k6");
        assertThat(actual.get(5).getCount()).isEqualTo(6L);
        assertThat(actual.get(6).getKeyword()).isEqualTo("k5");
        assertThat(actual.get(6).getCount()).isEqualTo(5L);
        assertThat(actual.get(7).getKeyword()).isEqualTo("k4");
        assertThat(actual.get(7).getCount()).isEqualTo(4L);
        assertThat(actual.get(8).getKeyword()).isEqualTo("k4");
        assertThat(actual.get(8).getCount()).isEqualTo(4L);
        assertThat(actual.get(9).getKeyword()).isEqualTo("k3");
        assertThat(actual.get(9).getCount()).isEqualTo(3L);
    }
}