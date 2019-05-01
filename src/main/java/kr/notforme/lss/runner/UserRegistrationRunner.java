package kr.notforme.lss.runner;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import kr.notforme.lss.business.model.user.UserRegistration;
import kr.notforme.lss.business.service.UserService;
import kr.notforme.lss.support.exceptions.NotExistUserException;

@Component
public class UserRegistrationRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(UserRegistrationRunner.class);

    private final UserService userService;
    private static Collection<UserRegistration> userRegistrations;

    static {
        userRegistrations = new HashSet<>();
        userRegistrations.add(new UserRegistration("admin", "admin"));
        userRegistrations.add(new UserRegistration("woojin.joe", "1234"));
    }

    public UserRegistrationRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        registerUsers(userRegistrations);
    }

    // visible for testing
    public Collection<UserRegistration> getUserRegistrations() {
        return userRegistrations;
    }

    // visible for testing
    public void registerUsers(Collection<UserRegistration> userRegistrations) throws NotExistUserException {
        if (CollectionUtils.isEmpty(userRegistrations)) {
            throw new NotExistUserException();
        }
        log.info("Start to register predefined users");

        userService.registerAll(userRegistrations);
    }
}
