package Utils;

import java.util.List;

public class ChatGPTPrompt {
    public static String getGPTPrompt(List<String> palavras) {
        return "I will provide you with a list of English words, and I want you to do the following for each word or expressions:" +
                "Generate JSON objects, one for each unique definition or meaning of the word. If a word has multiple" +
                "definitions (e.g., as a verb, noun, slang, adverb), create a separate JSON object for each definition." +
                "Each JSON object should contain the following fields: 'word', 'definition', 'phrase', and 'synonyms'." +
                "The 'phrase' field should include a sentence illustrating the usage of the word in a realistic scenario." +
                "Create one JSON object for each definition of the same word/expression." +
                "For example, for the words [nail, get], the output should be:" +
                "{" +
                "  \"word\": \"nail\"," +
                "  \"definition\": \"A thin pointed piece of metal used to fasten things together\"," +
                "  \"phrase\": \"He used a hammer to drive the nail into the wooden board.\"," +
                "  \"synonyms\": \"spike, tack\"" +
                "}END" +
                "{" +
                "  \"word\": \"nail\"," +
                "  \"definition\": \"To achieve or accomplish something successfully.\"," +
                "  \"phrase\": \"She nailed her presentation and impressed everyone in the meeting.\"," +
                "  \"synonyms\": \"accomplish, achieve\"" +
                "}END" +
                "{" +
                "  \"word\": \"nail\"," +
                "  \"definition\": \"A thin, hard area that covers the upper side of the end of each finger and each toe\"," +
                "  \"phrase\": \"Stop biting your nails!\"," +
                "  \"synonyms\": \"fingernail\"" +
                "}END" +
                "{" +
                "  \"word\": \"get\"," +
                "  \"definition\": \"To receive or obtain something\"," +
                "  \"phrase\": \"Did you get my email about the meeting tomorrow?\"," +
                "  \"synonyms\": \"obtain, acquire\"" +
                "}END" +
                "{" +
                "  \"word\": \"get\"," +
                "  \"definition\": \"To become or cause to become affected by a particular condition or state.\"," +
                "  \"phrase\": \"I always get nervous before exams.\"," +
                "  \"synonyms\": \"become, grow\"" +
                "}END" +
                "Here are the words: " + palavras +
                "Note: The 'END' marker at the end of each JSON is important for parsing, so please include it. Generate" +
                "more than one JSON for each word if the word or expression has more than one different definition.";

    }
}
