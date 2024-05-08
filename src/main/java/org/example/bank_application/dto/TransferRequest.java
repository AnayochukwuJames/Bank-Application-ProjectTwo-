package org.example.bank_application.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private String accountFrom;
    private String accountTo;
    private double amount;

}
