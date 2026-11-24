package com.smartmobile.managementsystem.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String toEmail, String otp) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("Email Verification - Smart Mobile Repair");

        String html = """
            <html>
            <body style="font-family:Arial,sans-serif;
                         background-color:#f4f4f4;padding:25px;">
              <div style="max-width:500px;margin:auto;
                          background:white;padding:30px;
                          border-radius:10px;">
                <h2 style="color:#2563eb;text-align:center;">
                  Smart Mobile Repair
                </h2>
                <h3>Email Verification</h3>
                <p>Hello,</p>
                <p>Thank you for registering with us.
                   Your verification OTP is:</p>
                <h1 style="text-align:center;color:#2563eb;
                           letter-spacing:8px;">%s</h1>
                <p>This OTP is valid for 5 minutes.</p>
                <p>Please do not share this code with anyone.</p>
                <br>
                <p>Regards,<br>
                   <b>Smart Mobile Repair Team</b></p>
              </div>
            </body>
            </html>
            """.formatted(otp);

        helper.setText(html, true);

        mailSender.send(message);
    }
}
