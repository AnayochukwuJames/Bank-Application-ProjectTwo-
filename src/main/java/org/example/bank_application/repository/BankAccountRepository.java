package org.example.bank_application.repository;
import org.example.bank_application.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

//    BankAccount findByUser(String username);


    BankAccount findBankAccountByAccountNumber(String accountNumber);

}
