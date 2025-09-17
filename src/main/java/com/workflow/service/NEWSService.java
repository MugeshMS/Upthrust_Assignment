package com.workflow.service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NEWSService {

    private static final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines";

    @Value("${newsapi.api.key}")
    private String apiKey;

    /**
     * Fetch top trending news headlines
     * Returns a short, comma-separated list of titles
     */
    public String getTrendingNews( int count ) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Build URL: top headlines, country, API key, number of articles
//            String url = NEWS_API_URL +   "&pageSize=" + count + "&apiKey=" + apiKey;
            String url = NEWS_API_URL + "?country=us&pageSize=3&apiKey=" + apiKey;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.GET, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode articles = mapper.readTree(response.getBody()).path("articles");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Math.min(count, articles.size()); i++) {
                String title = articles.get(i).path("title").asText();
                sb.append(title);
                if (i < count - 1) sb.append(" | "); // separate multiple headlines
            }

            return sb.toString();

        } catch (Exception e) {
            return "Error fetching trending news";
        }
    }
}
