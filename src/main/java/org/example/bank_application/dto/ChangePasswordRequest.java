package org.example.bank_application.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String username;
    private String resetCode;
    private String password;
    private String confirmPassword;

}
