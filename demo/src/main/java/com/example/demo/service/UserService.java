package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UserEntity create(final UserEntity userEntity){
        if(){

        }
        final String email = ;
        if(userRepository.existsByEmail(email)){
            log.warn("Email already exists {}", email);
        }
        return ;
    }

    public UserEntity getByCredentials(final String email, final String password,
                                       PasswordEncoder encoder) {

        final UserEntity originalUser = userRepository.findByEmail(email);

        if(originalUser != null &&
                encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }

        return  null;
    }


}