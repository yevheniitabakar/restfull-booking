package com.heroku.app.api.auth;

import static io.restassured.RestAssured.given;

import io.restassured.specification.RequestSpecification;
import com.heroku.app.model.AuthCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the Strategy design pattern for token-based authentication using a Cookie header.
 * <p>
 * This class is a concrete strategy for the {@link AuthStrategy} interface, handling authentication by fetching a token
 * from the /auth endpoint and applying it to API requests via a Cookie header (token=&lt;token_value&gt;). The Strategy
 * pattern allows this authentication method to be swapped with others (e.g., Basic Auth) at runtime.
 * </p>
 */
public class TokenAuthStrategy implements AuthStrategy {
    public static final Logger logger = LoggerFactory.getLogger(TokenAuthStrategy.class.getSimpleName());
    private final String token;

    public TokenAuthStrategy() {
        this.token = given()
                .contentType("application/json")
                .body(new AuthCredentials("admin", "password123"))
                .post("/auth")
                .jsonPath()
                .get("token");

        logger.info("TOKEN= {}", token);
    }

    @Override
    public void applyAuth(RequestSpecification spec) {
        logger.info("Token Authentication will be applied to the request");

        spec.header("Cookie", "token=" + token);
    }
}
