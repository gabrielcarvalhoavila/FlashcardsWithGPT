package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWordsFromFile {
    public static List<String> readWordsFromFile(String path) throws IOException {
        List<String> palavras = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            palavras.add(line);
        }
        return palavras;
    }
}
