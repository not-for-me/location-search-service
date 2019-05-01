package kr.notforme.lss.support.util;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PasswordCryptoUtilTest {

    @Test
    public void testEncode() {
        // Given
        String raw = "test-password";

        // When
        String actual = PasswordCryptoUtil.encode(raw);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEqualTo(raw);
    }

    @Test
    public void isMatch() {
        // Given
        String raw = "test-password";
        String encoded = PasswordCryptoUtil.encode(raw);

        // When
        boolean actual = PasswordCryptoUtil.isMatch(raw, encoded);

        // Then
        assertThat(actual).isTrue();


        // Given: enocde other password
        encoded = PasswordCryptoUtil.encode(raw + "-added-more");

        // When
        actual = PasswordCryptoUtil.isMatch(raw, encoded);

        // Then
        assertThat(actual).isFalse();
    }
}