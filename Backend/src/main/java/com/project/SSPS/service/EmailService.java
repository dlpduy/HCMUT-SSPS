package com.project.SSPS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SSPS.dto.EmailDTO;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(EmailDTO email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("spsohcmut@gmail.com");
            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody());
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}