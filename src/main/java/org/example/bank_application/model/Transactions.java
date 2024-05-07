package org.example.bank_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bank_application.enums.TransactionType;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transactions")

public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    @Length(min = 10)
    private String accountNumber;
    private String accountTo;
    private Date transactionDate;
    private double amount;
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


}
