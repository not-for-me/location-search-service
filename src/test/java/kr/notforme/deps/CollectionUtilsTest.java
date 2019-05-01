package kr.notforme.deps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

/**
 * This is dependent utils's test
 */
public class CollectionUtilsTest {

    @Test
    public void isEmpty_given_null_or_empty_collection_then_true() {
        // Given: null param
        Collection<Long> collection = null;

        // When
        boolean actual = CollectionUtils.isEmpty(collection);

        // Then
        assertThat(actual).isTrue();

        // Given: empty param
        collection = new HashSet<>();

        // When
        boolean actual2 = CollectionUtils.isEmpty(collection);

        // Then
        assertThat(actual2).isTrue();
    }
}
