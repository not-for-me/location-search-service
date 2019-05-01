package kr.notforme.deps;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class RandomStringUtilsTest {

    @Test
    public void randomAlphabetic_given_count_then_generate_alphabetic() {
        // Given
        final int strLen = 10;

        // When
        String actual = RandomStringUtils.randomAlphabetic(strLen);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual).matches("^[A-Za-z]+$");
        assertThat(actual.length()).isEqualTo(strLen);
    }

    @Test
    public void randomAlphabetic_given_count_then_generate_alphanumeric() {
        // Given
        final int strLen = 10;

        // When
        String actual = RandomStringUtils.randomAlphanumeric(strLen);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual).matches("^[A-Za-z0-9]+$");
        assertThat(actual.length()).isEqualTo(strLen);
    }
}
