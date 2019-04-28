package kr.notforme.lss.business.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.notforme.lss.business.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
}
