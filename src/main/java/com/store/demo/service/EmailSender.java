package com.store.demo.service;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@AllArgsConstructor
public class EmailSender {
    private  final JavaMailSender javaMailSender ;


    public  void sendEmail(String email, String subject, String text) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(text );
            javaMailSender.send(mailMessage);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
    public  void sendReceipt(String email, String subject, String text,String pdf) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  // 'true' enables multipart (for attachments)

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text);

            // Add attachment
            helper.addAttachment("Receipt.pdf", new File(pdf));

            // Send the email
            javaMailSender.send(message);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
