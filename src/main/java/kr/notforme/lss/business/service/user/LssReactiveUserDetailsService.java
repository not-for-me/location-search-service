package kr.notforme.lss.business.service.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.notforme.lss.business.model.user.User;
import kr.notforme.lss.business.repository.user.UserRepository;
import reactor.core.publisher.Mono;

@Service
public class LssReactiveUserDetailsService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    public LssReactiveUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // for security authentication
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findById(username)
                             .map(User::toUserDetails)
                             .map(Mono::just)
                             .orElseGet(Mono::empty);
    }
}
