package com.thangavel.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String accessToken;
    private Date createdAt;
    private Date updatedAt;

}
