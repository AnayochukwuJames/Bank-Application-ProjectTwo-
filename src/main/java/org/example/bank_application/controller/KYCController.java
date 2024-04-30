package org.example.bank_application.controller;

import lombok.*;
import org.example.bank_application.model.KnowYourCustomer;
import org.example.bank_application.service.KYCService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("kyc")
public class KYCController {

    private final KYCService kycService;

    @PutMapping("update")
    public ResponseEntity<KnowYourCustomer> updateKYC(@PathVariable Long id, @RequestBody KnowYourCustomer knowYourCustomer){
        return kycService.updateKYC(id,knowYourCustomer);
    }
    @PostMapping("create_kyc")
    public ResponseEntity<KnowYourCustomer>createKYC(@RequestBody KnowYourCustomer knowYourCustomer){
        return kycService.createKYC(knowYourCustomer);
    }
    @GetMapping("")
    public ResponseEntity<List<KnowYourCustomer>> getAllKYC(){
        return kycService.getAllKYC();
    }

    @GetMapping("{id}")
    public ResponseEntity<KnowYourCustomer> getById (@PathVariable Long id){
        return kycService.getById(id);
    }
    @GetMapping("accountUser")
    public ResponseEntity<KnowYourCustomer> getByAccountUser(@RequestParam String accountUser){
        return kycService.findByAccountUserUsername(accountUser);
    }
}
