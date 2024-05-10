package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import json.ChatContentResponse;
import json.ChatRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ChatGPTService {
    private static final String BASE_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String MODEL = "gpt-3.5-turbo-0125";


    public static String chatGPTRequest(String prompt) throws IOException {
        ObjectMapper mapper = new ObjectMapper();


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(createChatRequest(prompt))))
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static ChatRequest createChatRequest(String prompt) {
        return new ChatRequest(
                MODEL,
                List.of(new ChatRequest.Message("user", prompt)),
                0.7);
    }

    public static List<ChatContentResponse> filterResponseFromGPT(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode contentNode = rootNode.path("choices").get(0).path("message").path("content");
        List<ChatContentResponse> lista_dto = new ArrayList<>();

        if (!contentNode.isMissingNode()) {
            String content = contentNode.asText();
            final String[] split = content.split("END");

            for (String s : split) {
                lista_dto.add(objectMapper.readValue(s, ChatContentResponse.class));
            }

        } else {
            System.out.println("Content not found in JSON.");
        }
        return lista_dto;
    }


}