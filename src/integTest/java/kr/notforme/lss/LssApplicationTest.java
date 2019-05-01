package kr.notforme.lss;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import kr.notforme.lss.business.model.user.User;
import kr.notforme.lss.business.model.user.UserRegistration;
import kr.notforme.lss.business.repository.user.UserRepository;
import kr.notforme.lss.runner.UserRegistrationRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LssApplicationTest {
    @Test
    public void checkContextLoad() {
        // check context load
    }

    @Autowired
    private UserRegistrationRunner userRegistrationRunner;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserIsRegisteredOnStartUpTime() {
        Collection<UserRegistration> userRegistrations = userRegistrationRunner.getUserRegistrations();

        userRegistrations
                .stream()
                .map(UserRegistration::toUser)
                .map(User::getId)
                .map(id -> userRepository.findById(id))
                .forEach(userOptional -> {
                    assertThat(userOptional).isNotNull();
                    assertThat(userOptional.isPresent()).isTrue();
                });
    }
}
