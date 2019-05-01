package kr.notforme.lss.business.service;


import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import kr.notforme.lss.business.model.user.UserRegistration;
import kr.notforme.lss.business.repository.user.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService sut;

    @Mock
    private UserRepository userRepository;

    @Test
    public void registerAll_given_null_or_empty_user_then_do_nothing() {
        // Given: null
        Collection<UserRegistration> fixture = null;

        // When
        sut.registerAll(fixture);

        // Then
        then(userRepository).should(never()).saveAll(anyCollection());

        // Given: empty
        fixture = new HashSet<>();

        // When
        sut.registerAll(fixture);

        // Then
        then(userRepository).should(never()).saveAll(anyCollection());
    }

    @Test
    public void registerAll_given_registration_collection_then_saveAll() {
        // Given: null
        Collection<UserRegistration> fixture = new HashSet<>();
        fixture.add(new UserRegistration("id1","p1"));
        fixture.add(new UserRegistration("id2","p2"));

        // When
        sut.registerAll(fixture);

        // Then
        then(userRepository).should().saveAll(anyCollection());
    }
}