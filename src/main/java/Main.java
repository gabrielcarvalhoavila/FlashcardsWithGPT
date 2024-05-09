import model.FlashCard;
import service.AnkiService;
import service.ImageService;

import java.io.IOException;
import java.util.List;

import static Utils.ChatGPTPrompt.getGPTPrompt;
import static Utils.FileUtils.readWordsFromFile;
import static service.ChatGPTService.chatGPTRequest;
import static service.ChatGPTService.filterResponseFromGPT;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        AnkiService ankiService = new AnkiService();
        ImageService imageService = new ImageService();
        List<String> words = readWordsFromFile("ingles.txt");
        String prompt = getGPTPrompt(words);
        String response = chatGPTRequest(prompt);

        List<FlashCard> flashCards =
                filterResponseFromGPT(response)
                        .stream()
                        .map(r -> new FlashCard(r, imageService.getImageUrlFromGoogle(r.word())))
                        .toList();
        System.out.println("Flashcards: " + flashCards);

        for (FlashCard f : flashCards) {
            ankiService.storeMediaFile(f);
            System.out.println("Stored media file for: " + f.getWord());
        }

        ankiService.addNotes(flashCards);

    }
}
