package utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties = new Properties();
    private static String environment;

    static {
        // Get environment from system property (default: dev)
        environment = System.getProperty("env", "dev");
        loadProperties(environment);
    }

    private static void loadProperties(String env) {
        try {
            String path = "src/test/resources/config/config-" + env + ".properties";
            FileInputStream fis = new FileInputStream(path);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load properties for environment: " + env, e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
