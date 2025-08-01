package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    private final RequestSpecification request;

    public ApiClient() {
        request = RestAssured.given()
//                .log().all()
                .header("Content-Type", "application/json");
    }

    public ApiClient setBaseUri(String baseUri) {
        request.baseUri(baseUri);
        return this;
    }

    public ApiClient setBody(Object body) {
        request.body(body);
        return this;
    }

    public ApiClient addHeader(String key, String value) {
        request.header(key, value);
        return this;
    }

    public Response post(String endpoint) {
        Response response = request.when().post(endpoint);
        response.then()
//                .log().all()
        ;
        return response;
    }

    public Response get(String endpoint) {
        Response response = request.when().get(endpoint);
        response.then()
//                .log().all()
        ;
        return response;
    }

    public Response put(String endpoint) {
        Response response = request.when().put(endpoint);
        response.then()
//                .log().all()
        ;
        return response;
    }

    public Response delete(String endpoint) {
        Response response = request.when().delete(endpoint);
        response.then()
//                .log().all()
        ;
        return response;
    }
}
