package com.workflow.service;



import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GithubService {

    private static final String GITHUB_API_URL = "https://api.github.com/search/repositories";

    /**
     * Fetch top trending repositories (created in last 7 days, sorted by stars)
     * Returns a short, comma-separated list like:
     * "repo1 ⭐540, repo2 ⭐320, repo3 ⭐210"
     */
    public String getTrendingRepos(int count) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Search repos created in the last 7 days, sorted by stars
            String query = "q=created:>=" + getLastWeekDate() + "&sort=stars&order=desc&per_page=" + count;
            String url = GITHUB_API_URL + "?" + query;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/vnd.github.v3+json");

            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.GET, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode items = mapper.readTree(response.getBody()).path("items");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Math.min(count, items.size()); i++) {
                JsonNode repo = items.get(i);
                String name = repo.path("full_name").asText();
                int stars = repo.path("stargazers_count").asInt();
                sb.append(name).append(" ⭐").append(stars);
                if (i < count - 1) sb.append(", ");
            }

            return sb.toString();

        } catch (Exception e) {
            return "Error fetching GitHub trending repos";
        }
    }

    // Helper method to get date 7 days ago in YYYY-MM-DD
    private String getLastWeekDate() {
        java.time.LocalDate date = java.time.LocalDate.now().minusDays(7);
        return date.toString();
    }
}
