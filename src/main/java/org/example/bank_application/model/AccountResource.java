package org.example.bank_application.model;

import org.springframework.hateoas.RepresentationModel;

public class AccountResource extends RepresentationModel<AccountResource> {
    private AccountUser accountUser;

    public AccountUser getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(AccountUser accountUser) {
        this.accountUser = accountUser;
    }
}