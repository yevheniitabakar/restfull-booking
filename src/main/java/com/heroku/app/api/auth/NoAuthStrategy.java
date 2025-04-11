package com.heroku.app.api.auth;

import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implements the Strategy design pattern for a no-authentication scenario.
 * <p>
 * This class is a concrete strategy for the {@link AuthStrategy} interface, representing a scenario where no
 * authentication is applied to API requests. It is used for testing endpoints that do not require authentication
 * or for negative tests where authentication is intentionally omitted. The Strategy pattern allows this to be
 * swapped with other authentication strategies at runtime.
 * </p>
 */
public class NoAuthStrategy implements AuthStrategy {
    public static final Logger logger = LoggerFactory.getLogger(TokenAuthStrategy.class.getSimpleName());

    @Override
    public void applyAuth(RequestSpecification spec) {
        logger.info("No Authentication will be applied to the request");
    }
}
