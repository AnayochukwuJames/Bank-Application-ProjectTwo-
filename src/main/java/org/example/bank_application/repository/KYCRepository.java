package org.example.bank_application.repository;

import org.example.bank_application.model.KnowYourCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KYCRepository extends JpaRepository<KnowYourCustomer, Long> {
    KnowYourCustomer findByAccountUserUsername(String accountUser);
}
