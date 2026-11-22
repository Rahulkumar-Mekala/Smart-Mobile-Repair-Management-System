package com.smartmobile.managementsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PythonAIService {

    private final RestClient restClient;

    public PythonAIService(
            RestClient.Builder builder,
            @Value("${python.ai.base-url}") String pythonUrl) {

        this.restClient = builder
                .baseUrl(pythonUrl)
                .build();
    }

    public String testPython() {
        return restClient.get()
                .uri("/ai/test")
                .retrieve()
                .body(String.class);
    }
}

