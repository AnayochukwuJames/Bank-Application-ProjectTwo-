package org.example.bank_application;

import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.KnowYourCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KYCRepository extends JpaRepository<KnowYourCustomer, Long> {
    KnowYourCustomer getByAccountUser(AccountUser accountUser);
}
