package com.smartmobile.managementsystem.service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartmobile.managementsystem.DTO.OtpVerificationRequest;
import com.smartmobile.managementsystem.Entity.EmailOtp;
import com.smartmobile.managementsystem.Entity.Role;
import com.smartmobile.managementsystem.Entity.User;
import com.smartmobile.managementsystem.repository.EmailOtpRepository;
import com.smartmobile.managementsystem.repository.RoleRepository;
import com.smartmobile.managementsystem.repository.UserRepository;

@Service
public class OtpService {

    private final EmailOtpRepository otpRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public OtpService(
            EmailOtpRepository otpRepository,
            EmailService emailService,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.otpRepository = otpRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void generateAndSendOtp(
            String email,
            String fullName,
            String phone,
            String password) throws Exception {

        try {
            String otp = String.format("%06d",
                    new java.security.SecureRandom().nextInt(1000000));

            EmailOtp emailOtp = new EmailOtp();
            emailOtp.setEmail(email);
            emailOtp.setFullName(fullName);
            emailOtp.setPhoneNumber(phone);
            emailOtp.setPassword(passwordEncoder.encode(password));
            emailOtp.setOtp(otp);
            emailOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));

            otpRepository.deleteByEmail(email);
            otpRepository.saveAndFlush(emailOtp);

            System.out.println("OTP_DATABASE_SAVE_SUCCESS");

            emailService.sendOtp(email, otp);

            System.out.println("OTP_EMAIL_SENT_SUCCESS");

        } catch (Exception e) {
            System.err.println("OTP_PROCESS_FAILED");
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public Map<String, Object> verifyOtp(OtpVerificationRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        EmailOtp emailOtp = otpRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("OTP not found"));

        if (LocalDateTime.now().isAfter(emailOtp.getExpiryTime())) {
            otpRepository.delete(emailOtp);
            throw new IllegalArgumentException("OTP expired");
        }

        if (!emailOtp.getOtp().equals(request.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Email is already registered");
        }

        Role role = roleRepository.findByRoleName("SHOP_OWNER")
                .orElseThrow(() ->
                        new IllegalStateException("SHOP_OWNER role not found"));

        User user = new User();
        user.setFullName(emailOtp.getFullName());
        user.setEmail(email);
        user.setPhoneNumber(emailOtp.getPhoneNumber());
        user.setPassword(emailOtp.getPassword());
        user.setActive(true);
        user.setRole(role);

        User savedUser = userRepository.save(user);

        otpRepository.delete(emailOtp);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Email verified and registration completed");
        response.put("userId", savedUser.getId());
        response.put("email", savedUser.getEmail());

        return response;
    }
}
