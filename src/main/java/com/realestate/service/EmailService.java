package com.realestate.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPropertyNotification(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true = isHtml
            
            mailSender.send(message);
        } catch (MessagingException e) {
            // Log the error but don't throw - email should not break core functionality
            e.printStackTrace();
        }
    }

    public void sendDealNotification(String to, String subject, String content) {
        sendPropertyNotification(to, subject, content);
    }
}
