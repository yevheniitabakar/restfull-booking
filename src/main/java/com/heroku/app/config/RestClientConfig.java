package com.heroku.app.config;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

/**
 * Implements the Singleton design pattern to manage a single instance of the RestAssured configuration.
 * <p>
 * The Singleton pattern ensures that only one instance of this class exists, providing a global point of access
 * to the RestAssured configuration (e.g., base URI, logging filters). This prevents redundant setup and ensures
 * consistency across all API calls in the test framework.
 */
public class RestClientConfig {
    private static RestClientConfig instance;

    private RestClientConfig() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    public static RestClientConfig getInstance() {
        if (instance == null) {
            instance = new RestClientConfig();
        }
        return instance;
    }
}
