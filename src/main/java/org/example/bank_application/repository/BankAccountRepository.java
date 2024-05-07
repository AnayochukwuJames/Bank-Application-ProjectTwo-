package org.example.bank_application.repository;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;


@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

//    BankAccount findByAccountUser(String username);

    BankAccount findByAccountUser(String username);


    Object getByAccountNumber(String accountNumber, HttpStatus httpStatus);

    BankAccount findBankAccountByAccountNumber(String accountNumber);

}
