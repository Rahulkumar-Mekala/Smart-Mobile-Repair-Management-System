package com.smartmobile.managementsystem.service;



import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartmobile.managementsystem.DTO.LoginRequest;
import com.smartmobile.managementsystem.DTO.RegisterRequest;
import com.smartmobile.managementsystem.Entity.Role;
import com.smartmobile.managementsystem.Entity.User;
import com.smartmobile.managementsystem.config.JwtService;
import com.smartmobile.managementsystem.repository.RoleRepository;
import com.smartmobile.managementsystem.repository.UserRepository;

@Service
public class AuthService {
	private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
    }

    @Transactional
    public Map<String, Object> register(RegisterRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }

        String phone = request.getPhoneNumber();

        if (phone != null && !phone.isBlank()
                && userRepository.existsByPhoneNumber(phone.trim())) {
            throw new IllegalArgumentException("Phone number is already registered");
        }

   
        String otp = String.valueOf(
                100000 + new java.security.SecureRandom().nextInt(900000)
        );

        // TODO: Save pending registration and OTP
        // TODO: Send OTP to email

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "OTP sent to your email");
        response.put("email", email);

        return response;
    }
    

public Map<String, Object> login(LoginRequest request) {

    String email = request.getEmail().trim().toLowerCase();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new IllegalArgumentException("Invalid email or password"));

    if (!Boolean.TRUE.equals(user.getActive())) {
        throw new IllegalArgumentException("Account is inactive");
    }

    if (!passwordEncoder.matches(
            request.getPassword(), user.getPassword())) {
        throw new IllegalArgumentException("Invalid email or password");
    }

    Map<String, Object> response = new HashMap<>();

    response.put("status", "success");
    response.put("message", "Login successful");
    response.put("userId", user.getId());
    response.put("fullName", user.getFullName());
    response.put("email", user.getEmail());
    response.put("role", user.getRole().getRoleName());
    String token = jwtService.generateToken(user.getEmail());

    response.put("token", token);
    response.put("tokenType", "Bearer");

    return response;
}

}