package com.example.demo.dto.request;

import com.example.demo.Enum.Role;
import com.example.demo.entity.UserProfile;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.validation.constraints.Pattern;

@Data
public class UserRequestDto {
    private String password;
    @Email(message = "please enter correct email")
    private String email;
    private Role role;
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be 10 digits"
    )
    private String phoneNumber;
    @NotBlank(message = "Address is required")
    private String address;
}
