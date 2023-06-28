package com.swm.lito.user.adapter.out.persistence;

import com.swm.lito.auth.application.port.out.AuthCommandPort;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCommandRepository implements AuthCommandPort {

    private final UserRepository userRepository;

    @Override
    public User save(User user){
        return userRepository.save(user);
    }
}
