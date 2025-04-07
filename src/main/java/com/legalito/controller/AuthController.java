package com.legalito.controller;

import com.legalito.model.Users;
import com.legalito.repository.UsersRepository;
import com.legalito.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        Users user = Users.builder()
                          .email(request.get("email"))
                          .passwordHash(passwordEncoder.encode(request.get("password")))
                          .isPremium(false)
                          .createdAt(LocalDateTime.now())
                          .build();
        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Users user = userRepository.findByEmail(request.get("email"))
                                   .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.get("password"), user.getPasswordHash())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
