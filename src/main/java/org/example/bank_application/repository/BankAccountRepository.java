package org.example.bank_application.repository;

import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    BankAccount findByAccountBalance (AccountUser accountUser);

}
