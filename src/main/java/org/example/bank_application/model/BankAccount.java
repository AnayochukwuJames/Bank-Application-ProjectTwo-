package org.example.bank_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Bank_Account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
//    @Pattern(regexp = "[0-9]{10}")
    private String accountNumber;
    private double accountBalance;

    @ManyToOne
//    @JoinColumn(name = "user_id")
    @JoinColumn(name = "account_user_id")
    private AccountUser accountUser;

    public BankAccount(String string, AccountUser accountUser, double openingAmount) {

    }
}
