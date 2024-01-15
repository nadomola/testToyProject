package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor //기본 생성자 추가해주는 어노테이션
@AllArgsConstructor // 클래스 안 쪽의 모든 필드의 생성자 자동 추가 어노테이션
@Data
@Entity // 엔티티 선언
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "phoneNum")})
public class UserEntity {
    @Id  // entity의 대푯값 지정
    @GeneratedValue(generator = "system-uuid")  //자동생성기능추가(숫자 자동으로 매겨짐)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column(nullable=false) //username 필드 선언, db테이블의 username과 연결됨/ 값을 false로 설정해 주면, 해당 필드는 DDL 생성 시 not null이라는 조건이 붙은 채로 생성된다.
    private String username;
    @Column(nullable=false) // phoneNum 필드 선언 ,db 테이블의 phoneNum과 연결됨
    private String phoneNum;
    @Column(nullable=false) //password 필드 선언, db테이블의 password와 연결됨
    private String password;
    @Column(nullable = false)
    private String birthday; // birthday 필드 선언, db테이블의 birthday와 연결

}