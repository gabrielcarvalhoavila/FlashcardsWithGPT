package model;

import lombok.Data;
import json.ChatContentResponse;

@Data
public class FlashCard {
    private String word;
    private String phrase;
    private String definition;
    private String synonyms;
    private String model;

    public FlashCard(ChatContentResponse response) {
        this.word = response.word();
        this.phrase = response.phrase();
        this.definition = response.definition();
        this.synonyms = response.synonyms();
    }
}
