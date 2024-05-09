package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import json.AddNotesRequest;
import json.StoreMediaAnkiRequest;
import model.FlashCard;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AnkiService {


    private final String baseUrl = "http://localhost:8765";
    HttpClient client = HttpClient.newHttpClient();


    public void getDeckNamesAndIds() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                            {
                            "action": "deckNamesAndIds",
                            "version": 6
                            }
                        """))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    public void getMediaDirPath() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                            {
                            "action": "getMediaDirPath",
                            "version": 6
                            }
                        """))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    public void getModelNames() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "action": "modelNames",
                            "version": 6
                        }
                        """))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    public void getMediaFileNames() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "action": "getMediaFilesNames",
                            "version": 6,
                            "params": {
                                "pattern": "*.jpg"
                            }
                        }
                        """))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    public void storeMediaFile(FlashCard flashCard) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(
                        new StoreMediaAnkiRequest(
                                "storeMediaFile",
                                6L,
                                new StoreMediaAnkiRequest.Params(
                                        flashCard.getFileName(),
                                        flashCard.getImageUrl())
                        ))))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    public void addNotes(List<FlashCard> flashCards) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(
                        new AddNotesRequest(
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
                                                ).toList())))))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

    }
}
