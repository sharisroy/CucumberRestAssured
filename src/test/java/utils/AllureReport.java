package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import io.restassured.response.Response;

import java.util.Map;

public class AllureReport {

    private static final ObjectMapper mapper = new ObjectMapper();

    private AllureReport() {
        // prevent instantiation
    }

    /** Attach response body */
    public static void attachResponse(String title, Response response) {
        try {
            String body = response.getBody().asPrettyString();
            Allure.addAttachment(title, body);
            // Attach status code
            attachStatusCode(response.getStatusCode());
        } catch (Exception e) {
            Allure.addAttachment("Failed to attach response", e.getMessage());
        }
    }

    /** Attach HTTP Status Code */
    public static void attachStatusCode(int statusCode) {
        try {
            Allure.addAttachment("Status Code", String.valueOf(statusCode));
        } catch (Exception e) {
            Allure.addAttachment("Failed to attach status code", e.getMessage());
        }
    }
    /** Attach HTTP Status Code */
    public static void attachStatusCode(String statusCode) {
        try {
            Allure.addAttachment("Response Code", String.valueOf(statusCode));
        } catch (Exception e) {
            Allure.addAttachment("Failed to attach status code", e.getMessage());
        }
    }

    /** Attach plain text (logs, SQL, debug info, etc.) */
    public static void attachText(String title, String content) {
        try {
            Allure.addAttachment(title, content);
        } catch (Exception e) {
            Allure.addAttachment("Failed to attach text", e.getMessage());
        }
    }

    /** Attach Payload */
    public static void attachPayload(Object obj) {
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            Allure.addAttachment("Payload", json);
        } catch (Exception e) {
            Allure.addAttachment("Failed to attach payload", e.getMessage());
        }
    }

    /** Attach Response (auto JSON/raw) */
    public static void attachResponse(Object obj) {
        String rawResponse = null;
        try {
            if (obj instanceof Response) {
                Response res = (Response) obj;
                rawResponse = res.getBody().asString();
                attachStatusCode(res.getStatusCode()); // Attach status code
            } else if (obj != null) {
                rawResponse = obj.toString();
            }

            if (rawResponse != null) {
                try {
                    Object jsonObject = mapper.readValue(rawResponse, Object.class);
                    String prettyJson = mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(jsonObject);
                    Allure.addAttachment("Response", prettyJson);
                } catch (Exception e) {
                    Allure.addAttachment("Raw Response", rawResponse);
                }
            }
        } catch (Exception ex) {
            Allure.addAttachment("Response (Fallback)", String.valueOf(obj));
        }
    }

    /** Attach Headers */
    public static void attachHeaders(Map<String, String> headers) {
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(headers);
            Allure.addAttachment("Headers", json);
        } catch (Exception e) {
            Allure.addAttachment("Failed to attach headers", e.getMessage());
        }
    }

    /** Attach Query Params */
    public static void attachQueryParams(Map<String, Object> queryParams) {
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryParams);
            Allure.addAttachment("Query Params", json);
        } catch (Exception e) {
            Allure.addAttachment("Failed to attach query params", e.getMessage());
        }
    }

    /** Attach Full URL */
    public static void attachFullUrl(String url) {
        try {
            Allure.addAttachment("Full URL", url);
        } catch (Exception e) {
            Allure.addAttachment("Failed to attach full URL", e.getMessage());
        }
    }
}
