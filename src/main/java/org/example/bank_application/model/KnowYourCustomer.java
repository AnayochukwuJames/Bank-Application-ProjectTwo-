package org.example.bank_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kyc")

public class KnowYourCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String nin;
    private String localGovtOfResidence;
    private String stateOfResidence;
    private String DateOfBirth;
    private String nextOfKin;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AccountUser accountUser;
}