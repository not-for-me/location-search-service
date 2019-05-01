package kr.notforme.lss.business.model.user;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class UserRegistrationTest {

    @Test
    public void toUser_given_registration_then_convert_to_user() {
        // Given
        final String idFixture = "id";
        final String passwordFixture = "password";

        // When
        User actual = new UserRegistration(idFixture, passwordFixture).toUser();

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(idFixture);
        assertThat(actual.getPassword()).isEqualTo(passwordFixture);
    }
}