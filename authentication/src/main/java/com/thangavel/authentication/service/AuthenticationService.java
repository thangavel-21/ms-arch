package com.thangavel.authentication.service;


import com.thangavel.authentication.config.JwtUtils;
import com.thangavel.authentication.dao.UserDao;
import com.thangavel.authentication.dto.AuthResponse;
import com.thangavel.authentication.dto.LoginRequestDto;
import com.thangavel.authentication.dto.RegisterRequestDto;
import com.thangavel.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils service;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse signup(RegisterRequestDto request) {
        var user = UserDao.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = service.generateToken(user);
        return authResponse(user, jwtToken);
    }

    public AuthResponse authenticateUser(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = service.generateToken(user);
        return authResponse(user, jwtToken);
    }

    private AuthResponse authResponse(UserDao user, String jwtToken) {
        return AuthResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .email(user.getEmail())
                .accessToken(jwtToken)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt()).build();
    }

}
