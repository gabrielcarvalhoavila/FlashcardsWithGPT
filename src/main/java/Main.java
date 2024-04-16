import java.io.IOException;
import java.util.List;

import static service.ChatGPTService.chatGPTRequest;
import static Utils.ChatGPTPrompt.getPrompt;
import static service.ChatGPTService.filterResponseFromGPT;
import static Utils.ReadWordsFromFile.readWordsFromFile;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        List<String> words = readWordsFromFile("ingles.txt");
        String prompt = getPrompt(words);
        String response = chatGPTRequest(prompt);
        //System.out.println(response);

        filterResponseFromGPT(response).forEach(System.out::println); // List<ChatResponse>
    }
}
