package com.workflow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Value("${openweathermap.api.key}")
    private String apiKey;

    /**
     * Fetches current weather for a city and returns a tweet-friendly string
     */
    public String getWeatherTweet(String city) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Build URL with city, API key, and metric units
            String url = API_URL + "?q=" + city + "&appid=" + apiKey + "&units=metric";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.GET, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            // Extract main weather info
            String weatherMain = root.path("weather").get(0).path("main").asText();
            String weatherDesc = root.path("weather").get(0).path("description").asText();
            double temp = root.path("main").path("temp").asDouble();

            // Get emoji for the main weather type
            String emoji = getWeatherEmoji(weatherMain, weatherDesc);

            // Format tweet-friendly text
            return String.format("%s %s, %.0f°C", emoji, capitalizeFirstLetter(weatherDesc), temp);

        } catch (Exception e) {
            return "Error fetching weather: " + e.getMessage();
        }
    }

    // Map weather conditions to emojis
    private String getWeatherEmoji(String main, String desc) {
        main = main.toLowerCase();
        desc = desc.toLowerCase();

        if (main.contains("clear") || desc.contains("sunny")) return "🌞";
        if (main.contains("cloud")) return "☁️";
        if (main.contains("rain") || desc.contains("drizzle")) return "🌧️";
        if (main.contains("thunderstorm")) return "⛈️";
        if (main.contains("snow")) return "❄️";
        if (main.contains("mist") || main.contains("fog") || main.contains("haze")) return "🌫️";
        return "🌤️"; // default
    }

    // Capitalize first letter of a sentence
    private String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
