package com.smartmobile.managementsystem.controller;


import com.smartmobile.managementsystem.service.OtpService;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartmobile.managementsystem.DTO.LoginRequest;
import com.smartmobile.managementsystem.DTO.OtpVerificationRequest;
import com.smartmobile.managementsystem.DTO.RegisterRequest;
import com.smartmobile.managementsystem.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final OtpService otpService;
	private final AuthService authService;

    public AuthController(AuthService authService, OtpService otpService) {
        this.authService = authService;
		this.otpService = otpService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        try {
            Map<String, Object> response =
                    authService.register(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "status", "error",
                            "message", e.getMessage()
                    ));
        }
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(
            @Valid @RequestBody OtpVerificationRequest request) {

        return ResponseEntity.ok(
                otpService.verifyOtp(request)
        );
    }

@PostMapping("/login")
public ResponseEntity<?> login(
        @Valid @RequestBody LoginRequest request) {

    try {
        Map<String, Object> response = authService.login(request);

        return ResponseEntity.ok(response);

    } catch (IllegalArgumentException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "status", "error",
                        "message", e.getMessage()
                ));
    }
 }

}