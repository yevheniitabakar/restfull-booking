package com.heroku.app.api.auth;

import io.restassured.specification.RequestSpecification;

/**
 * Interface for the Strategy design pattern, defining a contract for applying authentication to API requests.
 * <p>
 * The Strategy pattern allows the test framework to switch between different authentication mechanisms (e.g., token-based,
 * basic auth, or no auth) at runtime. This interface provides a method to apply authentication to a RestAssured
 * {@link RequestSpecification}, enabling flexible and interchangeable authentication strategies.
 * </p>
 */
public interface AuthStrategy {
    void applyAuth(RequestSpecification spec);
}
