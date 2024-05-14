package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import json.AddNotesRequest;
import json.StoreMediaAnkiRequest;
import model.FlashCard;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class AnkiService {


    private final String baseUrl = "http://localhost:8765";
    private final RestTemplate restTemplate = new RestTemplate();


    public void getDeckNamesAndIds() {

        String requestBody = """
                    {
                    "action": "deckNamesAndIds",
                    "version": 6
                    }
                """;
        System.out.println(restTemplate.postForObject(baseUrl, requestBody, String.class));
    }

    public void getMediaDirPath() {
        String requestBody = """
                    {
                    "action": "getMediaDirPath",
                    "version": 6
                    }
                """;
        System.out.println(restTemplate.postForObject(baseUrl, requestBody, String.class));
    }

    public void getModelNames() {
        String requestBody = """
                    {
                    "action": "modelNames",
                    "version": 6
                    }
                """;
        System.out.println(restTemplate.postForObject(baseUrl, requestBody, String.class));
    }

    public void getMediaFileNames() {
        String requestBody = """
                    {
                    "action": "getMediaFilesNames",
                    "version": 6,
                    "params": {
                        "pattern": "*.jpg"
                    }
                    }
                """;
        System.out.println(restTemplate.postForObject(baseUrl, requestBody, String.class));
    }

    public void storeMediaFile(FlashCard flashCard) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StoreMediaAnkiRequest requestBody = new StoreMediaAnkiRequest(
                "storeMediaFile",
                6L,
                new StoreMediaAnkiRequest.Params(
                        flashCard.getFileName(),
                        flashCard.getImageUrl()
                )
        );
        System.out.println(restTemplate.postForObject(baseUrl, mapper.writeValueAsString(requestBody), String.class));
    }

    public void addNotes(List<FlashCard> flashCards) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AddNotesRequest requestBody = new AddNotesRequest(
                "addNotes",
                6L,
                new AddNotesRequest.Params(
                        flashCards.stream()
                                .map(f -> new AddNotesRequest.Params.Notes(
                                                "ENGLISH",
                                                "Basic",
                                                new AddNotesRequest.Params.Notes.Fields(
                                                        f.phraseWithColoredWord(),
                                                        "Definition: " + f.getDefinition() + "<br><br>" +
                                                                "Synonyms: " + f.getSynonyms() + "<br><br>"
                                                                + "<img src=\"" + f.getFileName() + "\">"
                                                ),
                                                new AddNotesRequest.Params.Notes.Options(
                                                        false
                                                )
                                        )
                                ).toList()));

        System.out.println(restTemplate.postForObject(baseUrl, mapper.writeValueAsString(requestBody), String.class));

    }
}