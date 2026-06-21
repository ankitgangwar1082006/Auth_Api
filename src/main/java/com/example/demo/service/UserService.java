package com.example.demo.service;

import com.example.demo.Enum.Role;
import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.Users;
import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(UserRequestDto  userRequestDto)
    {
        Users users = new Users();

        //creating user profile
        UserProfile userProfile = new UserProfile();
        userProfile.setAddress(userRequestDto.getAddress());
        userProfile.setPhoneNumber(userRequestDto.getPhoneNumber());
        userProfile.setUsers(users);


        //creating user
        users.setUserProfile(userProfile);
        users.setEmail(userRequestDto.getEmail());
        users.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        if (userRequestDto.getRole() != null) {
            users.setRole(userRequestDto.getRole());
        } else {
            users.setRole(Role.USER);
        }

        Users savedUsers =userRepository.save(users);
        return createUserResponse(savedUsers);
    }
    public UserResponseDto getUserById(Long id)
    {
        Users users = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id : " + id));

        return createUserResponse(users);
    }
    public UserResponseDto updateUser(Long id, UserRequestDto dto)
    {
        Users users = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id : " + id));

        users.setEmail(dto.getEmail());
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        if(dto.getRole()!=null) users.setRole(dto.getRole());

        UserProfile profile = users.getUserProfile();

        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setAddress(dto.getAddress());

        Users updatedUsers = userRepository.save(users);

        return createUserResponse(updatedUsers);
    }
    public String deleteUser(Long id)
    {
        Users users = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id : " + id));

        userRepository.delete(users);

        return "User Deleted Successfully";
    }
    public UserResponseDto createUserResponse(Users users)
    {
        UserResponseDto responseDto = new UserResponseDto();

        responseDto.setId(users.getId());
        responseDto.setEmail(users.getEmail());
        responseDto.setRole(users.getRole());

        if(users.getUserProfile() != null)
        {
            responseDto.setAddress(users.getUserProfile().getAddress());
            responseDto.setPhoneNumber(users.getUserProfile().getPhoneNumber());
        }

        return responseDto;
    }
}
