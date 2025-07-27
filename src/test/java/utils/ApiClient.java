package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    private RequestSpecification request;

    public ApiClient() {
        request = RestAssured.given()
                .header("Content-Type", "application/json");
    }

    public ApiClient setBaseUri(String baseUri) {
        request.baseUri(baseUri);
        return this;
    }

    public ApiClient setBody(String body) {
        request.body(body);
        return this;
    }

    public ApiClient addHeader(String key, String value) {
        request.header(key, value);
        return this;
    }

    public Response post(String endpoint) {
        return request.when().post(endpoint);  // ✅ Correct Response type
    }

    public Response get(String endpoint) {
        return request.when().get(endpoint);
    }

    public Response put(String endpoint) {
        return request.when().put(endpoint);
    }

    public Response delete(String endpoint) {
        return request.when().delete(endpoint);
    }
}
