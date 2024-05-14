package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import json.ChatContentResponse;
import json.ChatRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChatGPTService {
    private static final String BASE_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String MODEL = "gpt-3.5-turbo-0125";
    private static final RestTemplate restTemplate = new RestTemplate();


    public static String chatGPTRequest(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("Content-Type", "application/json");

        return restTemplate.postForObject(BASE_URL, new HttpEntity<>(createChatRequest(prompt), headers), String.class);
    }

    public static ChatRequest createChatRequest(String prompt) {
        return new ChatRequest(
                MODEL,
                List.of(new ChatRequest.Message("user", prompt)),
                1);
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