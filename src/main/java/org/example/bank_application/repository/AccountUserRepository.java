package org.example.bank_application.repository;

import org.example.bank_application.model.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface AccountUserRepository extends JpaRepository<AccountUser, Long> {
        AccountUser findByUsername(String username);
        AccountUser findAccountUserByPhoneNumber(String phoneNumber);
        boolean existsByUsernameOrPhoneNumber(String username, String phoneNumber);

    }
