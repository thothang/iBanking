package com.example.iBankingPay.service;

import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
        } catch (Exception e) {
            throw new MailSendException("Failed to send email to " + to, e);
        }
    }
}
//@Service
//public class MailService {
//    public void sendEmail(String to, String subject, String text) {
//        System.out.println("FAKE EMAIL to=" + to + " | subject=" + subject + " | text=" + text);
//    }
//}
