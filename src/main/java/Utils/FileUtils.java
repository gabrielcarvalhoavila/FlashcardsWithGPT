package Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FileUtils {
    public static List<String> readWordsFromFile(String path) throws IOException {
        List<String> palavras = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            palavras.add(line);
        }
        return palavras;
    }

    public static boolean writeImageToFile(String imageData) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String filename = "image_" + timestamp + ".png";
        Path directory = Paths.get("src/main/resources");
        Path filePath = directory.resolve(filename);
        try{
            Files.createDirectories(directory);
            byte[] bytes = Base64.getDecoder().decode(imageData);
            Files.write(filePath, bytes, StandardOpenOption.CREATE_NEW);
            System.out.println("Image: " + filename + " written to: " + filePath);
            return true;

        }catch (IOException e){
            throw new UncheckedIOException("Error writing image to file", e);
        }
    }
}
