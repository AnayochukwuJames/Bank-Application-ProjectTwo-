package org.example.bank_application.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final JavaMailSender javaMailSender;

    @Async
    public void loginNotification(String receiver, String  message) throws MessagingException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true,"utf-8");
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setSubject("Login Notification");
        mimeMessageHelper.setText(message);
        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }
    public void registrationNotification(String receiver, String firstName) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        messageHelper.setTo(receiver);
        messageHelper.setSubject("Registration Successful!");
        String message = String.format("Dear %s,\nCongratulations!\nYou have successfully registered with Email address: %s\nYour account number is: %s", firstName, receiver, accountNumber);
        messageHelper.setText(message);
        javaMailSender.send(messageHelper.getMimeMessage());
    }
//    @Async
//    public void registrationNotification( String receiver, String firstName ) throws MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
//        messageHelper.setTo(receiver);
//        messageHelper.setSubject("Registration Successful!");
//        String message = String.format("Dear %s,\nCongratulations!\nYou have successfully registered with Email address: %s", firstName, receiver);
//        messageHelper.setText(message);
//        javaMailSender.send(messageHelper.getMimeMessage());
//    }

    @Async
    public void depositNotification(String firstName, String username, double amount) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        messageHelper.setTo(username);
        messageHelper.setSubject("Deposit Alert!");
        String message = String.format("Dear %s,\nA deposit of %s has been credited into your account number.", firstName, amount);
        messageHelper.setText(message);

        javaMailSender.send(messageHelper.getMimeMessage());
    }

    @Async
    public void withdrawalNotification(String firstName, String username, double amount) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        messageHelper.setTo(username);
        messageHelper.setSubject("Debit Alert!");
        String message = String.format("Dear %s,\nA withdrawal of %s has been debited from your account number.", firstName, amount);
        messageHelper.setText(message);

        javaMailSender.send(messageHelper.getMimeMessage());
    }

    public void sendResetCode(String username, String code) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        messageHelper.setTo(username);
        messageHelper.setSubject("Password Reset Code!");
        String message = String.format("Dear %s,\nHere is your password reset code: %s.", username, code);
        messageHelper.setText(message);

        javaMailSender.send(messageHelper.getMimeMessage());
    }
}
