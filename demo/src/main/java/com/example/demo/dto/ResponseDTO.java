package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    private String error; //에러 객체 생성
    private List<T> data;  // data의 묶음 - DB에서 조회한 데이터 묶음을 리스트에 담긴 T타입으로 가져옴
}