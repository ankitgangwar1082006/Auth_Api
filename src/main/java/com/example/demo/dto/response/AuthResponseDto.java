package com.example.demo.dto.response;

import lombok.Data;

@Data
public class AuthResponseDto {
    String token;
    String msg="login Success!";
}
