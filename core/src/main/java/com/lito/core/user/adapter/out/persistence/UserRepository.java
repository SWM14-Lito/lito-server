package com.lito.core.user.adapter.out.persistence;

import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByOauthIdAndProvider(String oauthId, Provider provider);
    Optional<User> findByEmailAndProvider(String email, Provider provider);
}
