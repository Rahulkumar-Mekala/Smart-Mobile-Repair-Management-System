package com.smartmobile.managementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartmobile.managementsystem.service.PythonAIService;


@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final PythonAIService pythonAIService;

    public AIController(PythonAIService pythonAIService) {
        this.pythonAIService = pythonAIService;
    }

    @GetMapping("/test")
    public String testPython() {
        return pythonAIService.testPython();
    }
}
