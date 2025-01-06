package com.example.identityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender mailSender;

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        // Use JavaMailSender to send the email
        try {
            mailSender.send(message);
            log.info("Mail sent to {}", to);
        } catch (Exception e) {
            log.info("Failed to send email to {}", to + ", error: " + e.getMessage());
        }
    }
}