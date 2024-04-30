package org.example.bank_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String accountFrom;
    private String accountTo;
    private Date transactionDate;
    private double amount;
    private String transactionId;

}
