package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ImageService {
    private final static String CUSTOM_SEARCH_API_KEY = System.getenv("CUSTOM_SEARCH_API_KEY");
    private final static String SEARCH_ENGINE_ID = "400ca9bb708684fd0";
    private final static String BASE_URL = "https://www.googleapis.com/customsearch/v1";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<String> getImageUrlFromGoogle(String query, int num) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?key=" + CUSTOM_SEARCH_API_KEY + "&cx=" + SEARCH_ENGINE_ID
                        + "&q="+query.replaceAll(" ", "%20") + "&searchType=image" + "&num=" + num +
                        "&siteSearchFilter=e&siteSearch=www.facebook.com%2C%20www.instagram.com%2C%20www.reddit.com%2C%20www.tiktok.com"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response body aqui: " + response.body());
            return filterImageUrlJson(response.body(), num);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> filterImageUrlJson(String json, int num) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            JsonNode contentNode = rootNode.path("items").get(i).path("link");
            if (!contentNode.isMissingNode()) {
                urls.add(contentNode.toString().replaceAll("\"", ""));
            }
        }

        return urls;
    }





}