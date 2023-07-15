package com.swm.lito.user.adapter.out.persistence;

import com.swm.lito.user.domain.User;
import com.swm.lito.user.domain.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByOauthIdAndProvider(String oauthId, Provider provider);
}
