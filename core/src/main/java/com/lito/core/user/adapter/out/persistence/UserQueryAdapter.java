package com.lito.core.user.adapter.out.persistence;

import com.lito.core.user.application.port.out.UserQueryPort;
import com.lito.core.auth.application.port.out.AuthQueryPort;
import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Optional<User> findByOauthIdAndProvider(String oauthId, Provider provider) {
        return userRepository.findByOauthIdAndProvider(oauthId, provider);
    }

    @Override
    public Optional<User> findByEmailAndProvider(String email, Provider provider){
        return userRepository.findByEmailAndProvider(email, provider);
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }
}
