package kr.notforme.lss.business.repository.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import kr.notforme.lss.Fixtures;
import kr.notforme.lss.business.model.user.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_given_user_then_save_it() {
        // Given
        User user = Fixtures.generateUser("test-user");

        // When
        userRepository.save(user);

        // Then
        assertThat(user.getSeq()).isPositive();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void save_given_too_long_id_then_throw_DataIntegrityViolationException() {
        // Given
        User user = Fixtures.generateUser(RandomStringUtils.randomAlphanumeric(User.MAX_ID_LEN + 1));

        // When
        userRepository.save(user);
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void save_given_too_long_password_then_throw_DataIntegrityViolationException() {
        // Given
        User user = Fixtures.generateUser();
        user.setPassword(RandomStringUtils.randomAlphanumeric(User.MAX_PASSWORD_LEN + 1));

        // When
        userRepository.save(user);
    }

    @Test
    public void FindById_given_userId_then_return_user() {
        // Given
        final String userId = "my-user";
        User user = Fixtures.generateUser(userId);
        entityManager.persist(user);

        // When
        Optional<User> actual = userRepository.findById(user.getId());

        // Then
        assertThat(actual.isPresent()).isTrue();
        actual.ifPresent(u -> {
            assertThat(u.getId()).isEqualTo(user.getId());
            assertThat(u.getPassword()).isEqualTo(user.getPassword());
        });
    }
}