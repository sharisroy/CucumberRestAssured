package utils;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private final Map<String, Object> context = new HashMap<>();

    public void set(String key, Object value) {
        context.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(context.get(key));
    }

    public boolean contains(String key) {
        return context.containsKey(key);
    }
}
