package com.example.demo.dto.response;

import com.example.demo.Enum.Role;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private Role role;
    private String phoneNumber;
    private String address;
}
