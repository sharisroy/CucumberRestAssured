package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {
    public static String readJsonFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
}
