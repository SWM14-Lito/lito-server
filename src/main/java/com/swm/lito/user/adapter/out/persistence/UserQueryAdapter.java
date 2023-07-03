package com.swm.lito.user.adapter.out.persistence;

import com.swm.lito.auth.application.port.out.AuthQueryPort;
import com.swm.lito.user.application.port.out.UserQueryPort;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserQueryAdapter implements AuthQueryPort, UserQueryPort {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByOauthId(String oauthId) {
        return userRepository.findByOauthId(oauthId);
    }
}
