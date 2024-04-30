package org.example.bank_application.service;


import lombok.*;
import org.example.bank_application.repository.KYCRepository;
import org.example.bank_application.model.KnowYourCustomer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class KYCService {


    private final KYCRepository kycRepository;

    public ResponseEntity<List<KnowYourCustomer>> getAllKYC(){
        return new ResponseEntity<>(kycRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<KnowYourCustomer> getById(Long id){
        return new ResponseEntity<>(kycRepository.findById(id).get(), HttpStatus.OK);
    }

    public ResponseEntity<KnowYourCustomer> findByAccountUserUsername(String accountUser){
        return new ResponseEntity<>(kycRepository.findByAccountUserUsername(accountUser),HttpStatus.OK);
    }

    public ResponseEntity<KnowYourCustomer> updateKYC(Long id, KnowYourCustomer knowYourCustomer){
        KnowYourCustomer kyc = kycRepository.findById(id).get();
        kyc.setAddress(kyc.getAddress());
        kyc.setStateOfResidence(kyc.getStateOfResidence());
        kyc.setLocalGovtOfResidence(kyc.getLocalGovtOfResidence());
        kyc.setNin(kyc.getNin());
        kyc.setDateOfBirth(kyc.getDateOfBirth());
        kyc.setNextOfKin(kyc.getNextOfKin());
        return new ResponseEntity<>(kycRepository.save(knowYourCustomer),HttpStatus.OK);
    }

    public ResponseEntity<KnowYourCustomer> createKYC(KnowYourCustomer knowYourCustomer){
        return new ResponseEntity<>(kycRepository.save(knowYourCustomer), HttpStatus.CREATED);
    }

}
