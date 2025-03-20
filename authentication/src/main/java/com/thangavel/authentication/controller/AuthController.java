package com.thangavel.authentication.controller;

import com.thangavel.authentication.config.JwtUtils;
import com.thangavel.authentication.dto.AuthResponse;
import com.thangavel.authentication.dto.LoginRequestDto;
import com.thangavel.authentication.dto.RegisterRequestDto;
import com.thangavel.authentication.exception.CustomException;
import com.thangavel.authentication.service.AuthenticationService;
import com.thangavel.authentication.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authenticationService.authenticateUser(loginRequestDto));
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        Map<String, Object> claims = jwtUtils.validateToken(token);

        if (claims == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        return ResponseEntity.ok(claims);
    }
}

