package com.example.demo.service;

import com.example.demo.dto.request.AuthRequestDto;
import com.example.demo.dto.response.AuthResponseDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       CustomUserDetailsService userDetailsService,
                       JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    public AuthResponseDto validateUser(AuthRequestDto requestDto)
    {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email and Password");
        }

        UserDetails userDetails=userDetailsService.loadUserByUsername(email);
        String token = jwtService.generateToken(userDetails);
        return createAuthResponse(token);
    }
    private AuthResponseDto createAuthResponse(String token)
    {
        AuthResponseDto authResponseDto= new AuthResponseDto();
        authResponseDto.setToken(token);
        return authResponseDto;
    }
}
