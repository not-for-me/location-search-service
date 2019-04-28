package kr.notforme.lss.business.service.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import kr.notforme.lss.business.repository.user.UserRepository;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class LssReactiveUserDetailsServiceTest {
    @InjectMocks
    private LssReactiveUserDetailsService sut;
    @Mock
    private UserRepository userRepository;

    @Test
    public void findByUsername_given_id_has_no_user_then_return_empty_mono() {
        // Given
        final String usernameFixture = "testUser";
        given(userRepository.findById(usernameFixture)).willReturn(Optional.empty());

        // When
        Mono<UserDetails> actual = sut.findByUsername(usernameFixture);

        // Then
        assertThat(actual.hasElement().block()).isFalse();
    }
}