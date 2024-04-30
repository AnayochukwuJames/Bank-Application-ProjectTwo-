package org.example.bank_application.repository;

import org.example.bank_application.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    Transactions findByTransactionId (String transactions);
}
