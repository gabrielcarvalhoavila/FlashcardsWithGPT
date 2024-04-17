package Utils;

import java.util.List;

public class ChatGPTPrompt {
    public static String getGPTPrompt(List<String> palavras) {
        return "I will give you a list of words in English, and I want you to do the following to each word:" +
                "Make JSON objects for each word or expression. And if the word has more than one different definition, make as many" +
                "json objects that is necessary. Each object has the fields: word, definition, phrase, synonyms." +
                "The field 'phrase' must be a phrase that describes a realistic scenario that is easy to draw." +
                "Make one object for each definition of the same word." +
                "For example: for the words [nail, get], the output should be:" +
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
                "  \"definition\": \"a thin, hard area that covers the upper side of the end of each finger and each toe\"," +
                "  \"phrase\": \"Stop biting your nails!\"," +
                "  \"synonyms\": \"fingernail\"" +
                "}END" +
                "{" +
                "  \"word\": \"get\"," +
                "  \"definition\": \"To receive or obtain something\"," +
                "  \"phrase\": \"Did you get my email about the meeting tomorrow?\"" +
                ",\"synonyms\": \"obtain, acquire\"" +
                "}END" +
                "{" +
                "  \"word\": \"get\"," +
                "  \"definition\": \"To become or cause to become affected by a particular condition or state.\"," +
                "  \"phrase\": \"I always get nervous before exams.\"," +
                "  \"synonyms\": \"become, grow\"" +
                "}END" +
                "Here are the words:'" +
                palavras +
                "PS: The word END at the end of each json is important to me for parsing, so don't skip it. Make" +
                "more than one json for each word, if the word has more than one different definition.";
    }
}
