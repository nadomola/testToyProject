package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j  //로깅 위한 어노테이션
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired  //의존성 주입  (스프링 부트가 만들어놓은 객체 가져와 주입해줌)
    private UserService userService;  //UserService 객세 선언
    @Autowired
    private TokenProvider tokenProvider;  //TokenProvider 객체 선언

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup") //url 요청 접수
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try { //예외 발생 가능성 있는
            UserEntity user = UserEntity.builder() //userEntity 객체 생성
                .phoneNum(userDTO.getPhoneNum())
                .username(userDTO.getUsername()).birthday(userDTO.getBirthday())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
            UserEntity registeredUser = userService.create(user);//userService 사용 하여 새로운 사용자를 등록
            UserDTO responseUserDTO = UserDTO.builder() //응답
                    .phoneNum(registeredUser.getPhoneNum()) //전화번호
                    .id(registeredUser.getId())  //아이디
                    .username(registeredUser.getUsername()) //사용자명
                    .birthday(registeredUser.getBirthday())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO); //등록 성공 ->ResponseEctity 반환

        }catch (Exception e){ // 예외 발생했을 경우, 처리하기 위한 문장
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build(); //
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin") //url 요청 접수
    public  ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){ //RequestBody에 있는 UserDTO를 받아와서 처리- <?>어떤 유형의 데이터든 반환할 수 있는 ResponseEntity
        UserEntity user = userService.getByCredentials(  //전화번호와 비밀번호 사용하여 사용자 인증
                userDTO.getPhoneNum(),
                userDTO.getPassword(),
                passwordEncoder

        );
        if( user != null){ //사용자가 인증에 성공한 경우
            final String token = tokenProvider.create(user); //토큰 생성
            final UserDTO responseUserDTO = UserDTO.builder() //응답용 UserDTO 객체 생성
                    .phoneNum(user.getPhoneNum())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO); // 인증 성공 시 ResponseEntity 반환
        }
        else{ // 사용자 인증 실패
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed").build(); // 오류 메시지 포함한 ResponseDTO 객체 생성
            //예외 발생한 경우 오류 응답 ResponseEntity 반환 (클라이언트에게 오류 발생했음을 알림)
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);

        }

    }
   // @GetMapping("/1")
    // 다음주 회의전까지 데이터가지고 놀아보기 ~
}