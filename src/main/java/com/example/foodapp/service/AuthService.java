package com.example.foodapp.service;

import com.example.foodapp.model.User;
import com.example.foodapp.repository.UserRepository;
import com.example.foodapp.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void registerUser(String email, String username, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("EMAIL_EXISTS");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("INVALID_LOGIN"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("INVALID_LOGIN");
        }

        return jwtUtil.generateToken(email);
    }
}

