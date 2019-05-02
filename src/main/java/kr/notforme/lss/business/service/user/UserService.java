package kr.notforme.lss.business.service.user;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.notforme.lss.business.model.user.User;
import kr.notforme.lss.business.repository.user.UserRepository;
import kr.notforme.lss.business.model.user.UserRegistration;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerAll(Collection<UserRegistration> userRegistrations) {
        if (CollectionUtils.isEmpty(userRegistrations)) {
            // noop
            return;
        }

        Collection<User> users = convertRegistrations(userRegistrations);
        userRepository.saveAll(users);
    }

    private Collection<User> convertRegistrations(Collection<UserRegistration> userRegistrations) {
        return userRegistrations.stream()
                                .map(UserRegistration::toUser)
                                .collect(Collectors.toSet());
    }
}
