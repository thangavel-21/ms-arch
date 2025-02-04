package com.thangavel.authentication.controller;

import com.thangavel.authentication.config.JwtUtils;
import com.thangavel.authentication.dto.AuthResponse;
import com.thangavel.authentication.dto.LoginRequestDto;
import com.thangavel.authentication.dto.RegisterRequestDto;
import com.thangavel.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequestDto registerUserDto) {
        return ResponseEntity.ok(authenticationService.signup(registerUserDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequestDto registerUserDto) {
        return ResponseEntity.ok(authenticationService.authenticateUser(registerUserDto));
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", ""); // Remove "Bearer " prefix
        Map<String, Object> claims = jwtUtils.validateToken(token);

        if (claims == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyMap());
        }

        return ResponseEntity.ok(claims);
    }

}
