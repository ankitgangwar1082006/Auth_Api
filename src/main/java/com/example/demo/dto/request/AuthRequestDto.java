package com.example.demo.dto.request;

import com.example.demo.Enum.Role;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AuthRequestDto {
    private String password;
    @Email(message = "please enter correct email")
    private String email;
}
