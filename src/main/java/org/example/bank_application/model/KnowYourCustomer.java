package org.example.bank_application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "kyc")

public class KnowYourCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String nin;
    private String localGovtOfResidence;
    private String stateOfResidence;
    private String DateOfBirth;
    private String nextOfKin;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AccountUser accountUser;

    public KnowYourCustomer(String address, String nin, String localGovtOfResidence, String stateOfResidence, String dateOfBirth, String nextOfKin, AccountUser accountUser) {
        this.address = address;
        this.nin = nin;
        this.localGovtOfResidence = localGovtOfResidence;
        this.stateOfResidence = stateOfResidence;
        DateOfBirth = dateOfBirth;
        this.nextOfKin = nextOfKin;
        this.accountUser = accountUser;
    }

    public KnowYourCustomer() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNin() {
        return nin;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }

    public String getLocalGovtOfResidence() {
        return localGovtOfResidence;
    }

    public void setLocalGovtOfResidence(String localGovtOfResidence) {
        this.localGovtOfResidence = localGovtOfResidence;
    }

    public String getStateOfResidence() {
        return stateOfResidence;
    }

    public void setStateOfResidence(String stateOfResidence) {
        this.stateOfResidence = stateOfResidence;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public AccountUser getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(AccountUser accountUser) {
        this.accountUser = accountUser;
    }
}
