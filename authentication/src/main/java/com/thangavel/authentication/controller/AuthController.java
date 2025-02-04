package com.thangavel.authentication.controller;

import com.thangavel.authentication.config.JwtUtils;
import com.thangavel.authentication.dto.AuthResponse;
import com.thangavel.authentication.dto.LoginRequestDto;
import com.thangavel.authentication.dto.RegisterRequestDto;
import com.thangavel.authentication.service.AuthenticationService;
import io.jsonwebtoken.Claims;
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


//    @GetMapping("/validate")
//    public Boolean validateToken(@RequestParam String token) {
//        try {
//            jwtService.validateToken(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDto loginUserDto, HttpServletRequest request) {
//
//        try {
//            // Extract JWT token from the Authorization header
//            String authHeader = request.getHeader("Authorization");
//
//            // Check if the Authorization header exists and starts with "Bearer "
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing or invalid Authorization header");
//            }
//
//            // Remove "Bearer " prefix to get the token
//            String token = authHeader.substring(7);
//
//            // Extract email from the token
//            String emailFromToken = jwtService.extractUsername(token);
//
//            // Validate email from JWT matches email in the login request
//            if (!Objects.equals(emailFromToken, loginUserDto.getEmail())) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Email in token does not match login email");
//            }
//
//            // Authenticate the user
//            AuthResponse authResponse = authenticationService.authenticateUser(loginUserDto);
//            return ResponseEntity.ok(authResponse);
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or user details");
//        }
//    }


}
