package kr.notforme.lss.runner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.BDDMockito.then;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.DefaultApplicationArguments;

import kr.notforme.lss.Fixtures;
import kr.notforme.lss.business.service.UserService;
import kr.notforme.lss.business.model.user.UserRegistration;
import kr.notforme.lss.support.exceptions.NotExistUserException;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationRunnerTest {
    @InjectMocks
    private UserRegistrationRunner sut;
    @Mock
    private UserService userService;

    @Test
    public void testApplicationRunImplementation() {
        assertThat(sut).isInstanceOf(ApplicationRunner.class);
    }


    @Test
    public void testRun_given_userRegistrations_then_call_register_all() throws Exception {
        // Given
        DefaultApplicationArguments fixture = new DefaultApplicationArguments(new String[] {});

        // When
        sut.run(fixture);

        // Then
        then(userService).should().registerAll(anyCollection());
    }

    @Test
    public void registerUser_given_userRegistrations_then_register_all() throws Exception {
        // Given
        Set<UserRegistration> userRegistrations = new HashSet<>();
        userRegistrations.add(Fixtures.generateUserRegistration());

        // When
        sut.registerUsers(userRegistrations);

        // Then
        then(userService).should().registerAll(anyCollection());
    }

    @Test(expected = NotExistUserException.class)
    public void registerUser_given_null_userRegistrations_then_throw_NotExistUserException() throws Exception {
        // Given
        Set<UserRegistration> userRegistrations = null;     // <-- null param

        // When
        sut.registerUsers(userRegistrations);
    }

    @Test(expected = NotExistUserException.class)
    public void registerUser_given_empty_userRegistrations_then_throw_NotExistUserException() throws Exception {
        // Given
        Set<UserRegistration> userRegistrations = new HashSet<>();  // <-- empty param

        // When
        sut.registerUsers(userRegistrations);
    }
}
