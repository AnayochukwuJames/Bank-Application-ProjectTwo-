package org.example.bank_application;

import org.example.bank_application.enums.Role;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.service.AccountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
@Async
public class BankApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountUserService accountUserService;

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AccountUser adminUser = new AccountUser();

        adminUser.setFirstName("Admin");
        adminUser.setLastName("Admin");
        adminUser.setUsername("adminuser@gmail.com");
        adminUser.setPassword(passwordEncoder.encode("NewPassWord24@#"));
        adminUser.setPhoneNumber("07066929216");
        adminUser.setRole(Role.ADMIN);

        AccountUser user = accountUserService.getAccountUserByUsername("adminuser@gmail.com").getBody();

        if (user == null){
            accountUserService.createUserAccount(adminUser);
        }
    }
}
