package com.heroku.app.checks;

import com.heroku.app.api.BookingApiFacade;
import com.heroku.app.api.response.BookingResponse;


/**
 * Concrete handler in the Chain of Responsibility design pattern, responsible for performing an API health check.
 * <p>
 * This class extends {@link PreTestCheckHandler} and implements a specific pre-test check by calling the /ping
 * endpoint of the RESTful Booker API. If the health check fails (e.g., the API is down), it throws an exception,
 * stopping the chain and preventing the test suite from running.
 */
public class HealthCheckHandler extends PreTestCheckHandler {

    @Override
    protected boolean performCheck(BookingApiFacade api) {
        BookingResponse response = api.healthCheck();

        if (response.getStatusCode() == 201) {
            System.out.println("Health check passed! All systems are ready.");
            return true;
        } else {
            System.err.println("Health check failed! API is not responding properly.");
            return false;
        }
    }
}
