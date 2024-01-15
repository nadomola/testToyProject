package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // 기본 생성자 추가해주는 어노테이션
@AllArgsConstructor // 생성자 자동 생성 어노테이션
public class UserDTO {
    private String token;
    private String phoneNum; //전화번호 받을 필드
    private String id; //아이디 받을 필드
    private String username;
    private String password;
    private String birthday;

}