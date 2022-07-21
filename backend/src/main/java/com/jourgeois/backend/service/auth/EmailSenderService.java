package com.jourgeois.backend.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Autowired
    EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(MimeMessage email) throws MailException {
        javaMailSender.send(email);
    }
}
