package com.example.foodapp.controller;

import com.example.foodapp.dto.LoginRequest;
import com.example.foodapp.dto.SignupRequest;
import com.example.foodapp.service.AuthService;
import org.springframework.http.HttpStatus;          // ✅ MISSING IMPORT
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        authService.registerUser(
                request.getEmail(),
                request.getUsername(),
                request.getPassword()
        );
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok(token);
    }
}
