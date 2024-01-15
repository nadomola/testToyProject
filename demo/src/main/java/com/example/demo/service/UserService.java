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
        if(userEntity == null || userEntity.getPhoneNum()==null){ //userEntity 또는 userEntity의 phoneNum이 null 일 경우
            throw new RuntimeException("Invalid arguments"); // 예외를 강제로 발생시킨다.

        }
        final String phoneNum = userEntity.getPhoneNum();
        if(userRepository.existsByPhoneNum(phoneNum)){
            log.warn("phoneNum already exists {}", phoneNum);
            throw new RuntimeException("phoneNum already exists");
        }
        return userRepository.save(userEntity);

    }

    public UserEntity getByCredentials(final String phoneNum, final String password,
                                       PasswordEncoder encoder) {

        final UserEntity originalUser = userRepository.findByPhoneNum(phoneNum);

        if(originalUser != null &&
                encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }

        return  null;
    }


}