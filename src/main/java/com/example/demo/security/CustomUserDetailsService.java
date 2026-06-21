package com.example.demo.security;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import org.hibernate.internal.util.Optional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username).orElseThrow(()->new RuntimeException("Wrong Email"));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+user.getRole());
        User userDetails = new User(username,user.getPassword(), Collections.singleton(authority));
        return userDetails;
    }
}
