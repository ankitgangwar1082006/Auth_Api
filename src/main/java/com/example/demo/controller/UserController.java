package com.example.demo.controller;

import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDto createUser(
            @Valid @RequestBody UserRequestDto userRequestDto)
    {
        return userService.createUser(userRequestDto);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id)
    {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto userRequestDto)
    {
        return userService.updateUser(id, userRequestDto);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id)
    {
        return userService.deleteUser(id);
    }
}