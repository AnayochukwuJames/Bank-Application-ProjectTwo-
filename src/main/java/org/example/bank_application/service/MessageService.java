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
    public void registrationNotification(String receiver, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setSubject("Registration Notification");
        mimeMessageHelper.setText(message);
        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }
}
