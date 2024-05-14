import model.FlashCard;
import service.AnkiService;
import service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static Utils.ChatGPTPrompt.getGPTPrompt;
import static Utils.FileUtils.readWordsFromFile;
import static service.ChatGPTService.chatGPTRequest;
import static service.ChatGPTService.filterResponseFromGPT;

public class Main {
    public static void main(String[] args) throws IOException {

        AnkiService ankiService = new AnkiService();
        ImageService imageService = new ImageService();
        List<String> words = readWordsFromFile("ingles.txt");
        String prompt = getGPTPrompt(words);
        String responseFromGPT = chatGPTRequest(prompt);

        Map<String, List<FlashCard>> wordFlashCardMap;


        wordFlashCardMap = filterResponseFromGPT(responseFromGPT)
                .stream()
                .map(FlashCard::new)
                .collect(Collectors.groupingBy(FlashCard::getWord));


        for (List<FlashCard> flashCards : wordFlashCardMap.values()) {
            int size = flashCards.size();
            List<String> imageUrlsFromGoogle = imageService.getImageUrlFromGoogle(flashCards.get(0).getWord(), size);
            for (int i = 0; i < size; i++) {
                FlashCard f = flashCards.get(i);
                f.setFileName(f.getFileName().replace(".jpg", "_" + i + ".jpg"));
                f.setImageUrl(imageUrlsFromGoogle.get(i));
            }
        }

        List<FlashCard> flashCards =
                wordFlashCardMap.values()
                .stream()
                .flatMap(List::stream)
                .toList();


        System.out.println("Flashcards: " + flashCards);

        for (FlashCard f : flashCards) {
            ankiService.storeMediaFile(f);
            System.out.println("Stored media file for: " + f.getWord());
        }

        ankiService.addNotes(flashCards);

    }
}
