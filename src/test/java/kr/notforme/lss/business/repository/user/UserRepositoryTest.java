package kr.notforme.lss.business.repository.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
    public void testSave() {
        // Given
        User user = Fixtures.generate("test-user");

        // When
        userRepository.save(user);

        // Then
        assertThat(user.getSeq()).isPositive();
    }

    @Test
    public void testFindById() {
        // Given
        User user = Fixtures.generate("my-user");
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