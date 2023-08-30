package com.lito.core.user.adapter.out.persistence;

import com.lito.core.auth.application.port.out.AuthCommandPort;
import com.lito.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCommandAdapter implements AuthCommandPort {

    private final UserRepository userRepository;

    @Override
    public User save(User user){
        return userRepository.save(user);
    }


}
