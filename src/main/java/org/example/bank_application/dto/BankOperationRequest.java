package org.example.bank_application.dto;

import lombok.Data;

@Data
public class BankOperationRequest {
    private String accountNumber;
    private double amount;

}
