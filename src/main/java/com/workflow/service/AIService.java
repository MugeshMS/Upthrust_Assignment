package com.workflow.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AIService {

    // OpenRouter GPT-3.5 free endpoint
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String API_KEY = "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"; // replace with your key

    public String generateText(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // JSON body for OpenRouter chat completion
            String requestJson = "{\n" +
                    "  \"model\": \"gpt-3.5-turbo\",\n" +
                    "  \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]\n" +
                    "}";

            HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(API_URL, request, String.class);

            // Parse JSON and extract AI text
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            String aiText = root.path("choices").get(0).path("message").path("content").asText();

            return aiText;

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
