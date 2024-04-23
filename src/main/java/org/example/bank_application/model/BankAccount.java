package org.example.bank_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity(name = "Bank_Account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Pattern(regexp = "[0-9]{10}")
    private String accountNumber;
    private double accountBalance;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountUser accountUser;

    public BankAccount(String accountNumber, double accountBalance, AccountUser accountUser) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.accountUser = accountUser;
    }

    public BankAccount() {}

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public AccountUser getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(AccountUser accountUser) {
        this.accountUser = accountUser;
    }
}
