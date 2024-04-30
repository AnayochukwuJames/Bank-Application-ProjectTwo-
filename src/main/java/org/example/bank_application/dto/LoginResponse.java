package org.example.bank_application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.bank_application.model.AccountUser;

@AllArgsConstructor
@Data
@Builder
public class LoginResponse {
    private AccountUser accountUser;
    private String token;
}
