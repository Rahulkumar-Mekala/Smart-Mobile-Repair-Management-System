package com.smartmobile.managementsystem.service;



import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartmobile.managementsystem.DTO.RegisterRequest;
import com.smartmobile.managementsystem.Entity.Role;
import com.smartmobile.managementsystem.Entity.User;
import com.smartmobile.managementsystem.repository.RoleRepository;
import com.smartmobile.managementsystem.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Map<String, Object> register(RegisterRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        // Check duplicate email
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Email is already registered");
        }

        // Check duplicate phone number
        String phone = request.getPhoneNumber();

        if (phone != null && !phone.isBlank()
                && userRepository.existsByPhoneNumber(phone.trim())) {

            throw new IllegalArgumentException(
                    "Phone number is already registered");
        }

        // Get SHOP_OWNER role
        Role role = roleRepository.findByRoleName("SHOP_OWNER")
                .orElseThrow(() ->
                        new IllegalStateException("SHOP_OWNER role not found"));

        // Create user
        User user = new User();

        user.setFullName(request.getFullName().trim());
        user.setEmail(email);
        user.setPhoneNumber(
                phone == null || phone.isBlank() ? null : phone.trim());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setActive(true);
        user.setRole(role);

        User savedUser = userRepository.save(user);

        // Registration response
        Map<String, Object> response = new HashMap<>();

        response.put("status", "success");
        response.put("message", "Registration successful");
        response.put("userId", savedUser.getId());
        response.put("fullName", savedUser.getFullName());
        response.put("email", savedUser.getEmail());
        response.put("role", savedUser.getRole().getRoleName());

        return response;
    }
}