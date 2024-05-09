package model;

import lombok.Data;
import json.ChatContentResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Data
public class FlashCard {
    private String word;
    private String phrase;
    private String definition;
    private String synonyms;
    private String imageUrl;
    private String fileName;


    public FlashCard(String word, String phrase, String definition, String synonyms, String imageUrl, String fileName) {
        this.word = word;
        this.phrase = phrase;
        this.definition = definition;
        this.synonyms = synonyms;
        this.imageUrl = imageUrl;
        this.fileName = fileName;
    }

    public FlashCard(ChatContentResponse response, String imageUrl) {
        this.word = response.word();
        this.phrase = response.phrase();
        this.definition = response.definition();
        this.synonyms = response.synonyms();
        this.imageUrl = imageUrl;
        this.fileName = "image_" + this.word.replaceAll(" ", "_") + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".jpg";
    }

    public FlashCard(String word, String phrase, String definition, String synonyms, String imageUrl) {
        this.word = word;
        this.phrase = phrase;
        this.definition = definition;
        this.synonyms = synonyms;
        this.imageUrl = imageUrl;
        this.fileName = "image_" + this.word.replaceAll(" ", "_") + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".jpg";
    }

    public String phraseWithColoredWord() {
        return this.phrase.replaceAll(this.word, replaceWordWithColoredWord(this.word));
    }

    private String generateRandomColor() {
        List<String> colors = List.of(
                "#00ff00", "#ff55ff", "#aa55ff", "#ff007f",
                "#ff0000", "#00ffff", "#0055ff", "#aa007f", "#aaaa00",
                "#00ffff", "#ffaaff", "#aaaa7f", "#00aaff", "#13e7ce", "#aaff00");
        Random random = new Random();
        int randomIndex = random.nextInt(colors.size());
        return colors.get(randomIndex);
    }

    private String replaceWordWithColoredWord(String word) {
        return "<span style=\"color:" + generateRandomColor() + "\">" + word + "</span>";

    }
}
