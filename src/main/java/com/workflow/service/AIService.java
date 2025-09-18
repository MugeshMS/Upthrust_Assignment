package com.workflow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AIService {

    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    // Inject from environment (Render → Environment Variables)
    @Value("${OPENROUTER_API_KEY}")
    private String apiKey;

    public String generateText(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);  // clean way to set Authorization: Bearer <key>
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Correct OpenRouter body (model must include provider prefix)
            String requestJson = "{\n" +
                    "  \"model\": \"openai/gpt-3.5-turbo\",\n" +
                    "  \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]\n" +
                    "}";

            HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(API_URL, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            return root.path("choices").get(0).path("message").path("content").asText();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
