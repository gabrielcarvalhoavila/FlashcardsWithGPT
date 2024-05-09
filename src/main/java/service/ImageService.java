package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImageService {
    private final static String CUSTOM_SEARCH_API_KEY = System.getenv("CUSTOM_SEARCH_API_KEY");
    private final static String SEARCH_ENGINE_ID = "400ca9bb708684fd0";
    private final static String BASE_URL = "https://www.googleapis.com/customsearch/v1";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String getImageUrlFromGoogle(String query) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?key=" + CUSTOM_SEARCH_API_KEY + "&cx=" + SEARCH_ENGINE_ID
                        + "&q="+query.replaceAll(" ", "%20") + "&searchType=image" + "&num=1" +
                        "&siteSearchFilter=e&siteSearch=www.facebook.com%2C%20www.instagram.com%2C%20www.reddit.com"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response body aqui: " + response.body());
            return filterImageUrlJson(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public String filterImageUrlJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode contentNode = rootNode.path("items").get(0).path("link");
        return contentNode.toString().replaceAll("\"", "");
    }





}
