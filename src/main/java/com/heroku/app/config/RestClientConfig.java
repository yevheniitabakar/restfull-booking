package com.heroku.app.config;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.Getter;

/**
 * Implements the Singleton design pattern to manage a single instance of the RestAssured configuration.
 * <p>
 * The Singleton pattern ensures that only one instance of this class exists, providing a global point of access
 * to the RestAssured configuration (e.g., base URI, logging filters). This prevents redundant setup and ensures
 * consistency across all API calls in the test framework.
 */
public class RestClientConfig {
    @Getter(lazy = true)
    private static final RestClientConfig instance = new RestClientConfig();

    // Private constructor to prevent instantiation
    private RestClientConfig() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}
