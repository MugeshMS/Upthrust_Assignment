package com.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WorkFlowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRunWorkflowWeather() throws Exception {
        // Sample request JSON
        String requestJson = objectMapper.writeValueAsString(
                new TestRequest("how is the weather", "weather")
        );

        mockMvc.perform(post("/run-workflow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.final_result").exists());
    }

  
    static class TestRequest {
        public String prompt;
        public String action;

        public TestRequest(String prompt, String action) {
            this.prompt = prompt;
            this.action = action;
        }
    }
}
