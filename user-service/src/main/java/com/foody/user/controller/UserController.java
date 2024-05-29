package com.foody.user.controller;

import com.example.userservice.model.User;
import com.example.userservice.security.JwtUtil;
import com.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<User>> register(@RequestBody User user) {
        return userService.registerUser(user.username(), user.email(), user.passwordHash())
                .map(savedUser -> ResponseEntity.status(201).body(savedUser));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody User user) {
        return userService.findByEmail(user.email())
                .flatMap(foundUser -> {
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    if (encoder.matches(user.passwordHash(), foundUser.passwordHash())) {
                        String token = jwtUtil.generateToken(foundUser.email());
                        return Mono.just(ResponseEntity.ok(token));
                    } else {
                        return Mono.just(ResponseEntity.status(401).build());
                    }
                });
    }

    @GetMapping("/profile")
    public Mono<ResponseEntity<User>> getProfile(Authentication authentication) {
        return userService.findByEmail(authentication.getName())
                .map(user -> ResponseEntity.ok(user))
                .defaultIfEmpty(ResponseEntity.status(404).build());
    }

    @PutMapping("/profile")
    public Mono<ResponseEntity<User>> updateProfile(Authentication authentication, @RequestBody User user) {
        return userService.findByEmail(authentication.getName())
                .flatMap(existingUser -> {
                    existingUser = new User(existingUser.userId(), user.username(), user.email(), user.passwordHash());
                    return userService.registerUser(existingUser.username(), existingUser.email(), existingUser.passwordHash());
                })
                .map(updatedUser -> ResponseEntity.ok(updatedUser))
                .defaultIfEmpty(ResponseEntity.status(404).build());
    }
}
