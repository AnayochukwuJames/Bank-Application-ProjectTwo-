package org.example.bank_application.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.bank_application.model.AccountResource;
import org.example.bank_application.model.AccountUser;
import org.example.bank_application.service.AccountUserService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("source")
public class AccountSourceController {
    private final AccountUserService accountUserService;

    public AccountSourceController(AccountUserService accountUserService) {
        this.accountUserService = accountUserService;
    }
    @GetMapping("{id}")
    public ResponseEntity<AccountResource> getAccountUserResource(@PathVariable Long id) throws MessagingException {
        AccountUser user = accountUserService.getAccountById(id).getBody();
        AccountResource accountResource = new AccountResource();
        accountResource.setAccountUser(user);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class)
                .getAccountUserById(id)).withSelfRel();
        Link createUserAccount = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class)
                .createUserAccount(user)).withRel("createUser");
        Link update = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class)
                .updateAccountUser(id, user)).withRel("update");
        Link getAll = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class)
                .getAllAccountUsers()).withRel("getAll");
        Link getById = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class)
                .getAccountUserById(id)).withRel("getById");
        Link delete = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class)
                .delete(id)).withRel("delete");
        Link username = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class)
                .findByUsername("username")).withRel("username");
        Link phoneNumber = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class)
                .getAccountUserByPhoneNumber("phoneNumber")).withRel("phoneNumber");
        accountResource.add(selfLink, createUserAccount,update,getAll,getById,delete,username,phoneNumber);
        return new ResponseEntity<>(accountResource, HttpStatus.OK);
    }
}