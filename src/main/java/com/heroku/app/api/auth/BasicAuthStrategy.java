package com.heroku.app.api.auth;

import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;


/**
 * Implements the Strategy design pattern for Basic Authentication.
 * <p>
 * This class is a concrete strategy for the {@link AuthStrategy} interface, handling authentication by encoding
 * a username and password into a Base64 string and applying it to API requests via an Authorization header
 * (Basic encoded_credentials). The Strategy pattern allows this authentication method to be swapped with
 * others (e.g., token-based auth) at runtime.
 */
@AllArgsConstructor
public class BasicAuthStrategy implements AuthStrategy {
    public static final Logger logger = LoggerFactory.getLogger(TokenAuthStrategy.class.getSimpleName());
    private final String username;
    private final String password;

    @Override
    public void applyAuth(RequestSpecification spec) {
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        logger.info("Basic Authentication will be applied to the request");

        spec.header("Authorization", "Basic " + encodedCredentials);
    }
}
