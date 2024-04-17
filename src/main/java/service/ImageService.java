package service;

import Utils.DallePrompt;
import Utils.FileUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import json.Image;
import json.ImageRequest;
import json.ImageResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImageService {
    private static final String BASE_URL = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String MODEL = "dall-e-2";
    private static final String SIZE = "256x256";
    private static final String RESPONSE_FORMAT = "b64_json";


    private final HttpClient httpClient = HttpClient.newHttpClient();


    public long generateImageWithDallE(String prompt) throws URISyntaxException, JsonProcessingException {
        ImageRequest request = new ImageRequest(MODEL, DallePrompt.getDallePrompt(prompt),1, SIZE, RESPONSE_FORMAT);
        ImageResponse imageResponse = sendImagePrompt(request);
        return imageResponse.data().stream()
                .map(Image::b64Json)
                .filter(FileUtils::writeImageToFile)
                .count();



    }

    public ImageResponse sendImagePrompt(ImageRequest imageRequest) throws JsonProcessingException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(imageRequest)))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), ImageResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
