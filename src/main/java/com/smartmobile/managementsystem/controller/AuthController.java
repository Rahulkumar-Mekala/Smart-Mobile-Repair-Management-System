package com.smartmobile.managementsystem.controller;


import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.smartmobile.managementsystem.DTO.RegisterRequest;
import com.smartmobile.managementsystem.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {

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
}
