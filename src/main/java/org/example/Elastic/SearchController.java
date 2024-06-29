package org.example.Elastic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    private String embeddingEndpointUrl = "http://127.0.0.1:1234/api/vectorize_query/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SearchController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "searchBox";  // Assuming "searchBox.html" template exists
    }

    @PostMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = "{\"query\":\"" + query + "\"}";
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    embeddingEndpointUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            List<Map<String, String>> results = new ArrayList<>();
            if (jsonResponse.has("results")) {
                for (JsonNode resultNode : jsonResponse.get("results")) {
                    Map<String, String> result = objectMapper.convertValue(resultNode, Map.class);
                    results.add(result);
                }
            }

            model.addAttribute("results", results);

        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while searching: " + e.getMessage());
        }

        return "searchResult";
    }
}