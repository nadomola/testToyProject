package com.example.demo.persistence;

import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {  //jpa리파지토리 상속 받아서
    UserEntity findByPhoneNum(String phoneNum);
    Boolean existsByPhoneNum(String phoneNum);
    UserEntity findByPhoneNumAndPassword(String phoneNum, String password);

}