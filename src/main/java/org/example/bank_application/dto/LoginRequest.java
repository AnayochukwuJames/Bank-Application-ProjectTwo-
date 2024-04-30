package org.example.bank_application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String username;
    private String password;

}
