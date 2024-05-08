package org.example.bank_application.repository;

import org.example.bank_application.model.AccountUser;
import org.example.bank_application.model.ResetCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetCodeRepository extends JpaRepository<ResetCode, Long> {
    ResetCodeRepository findByAccountUser(AccountUser accountUser);

}
