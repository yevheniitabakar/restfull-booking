package com.heroku.app.checks;

import com.heroku.app.api.BookingApiFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Concrete handler in the Chain of Responsibility design pattern, responsible for performing an API health check.
 * <p>
 * This class extends {@link PreTestCheckHandler} and implements a specific pre-test check by calling the /ping
 * endpoint of the RESTful Booker API. If the health check fails (e.g., the API is down), it throws an exception,
 * stopping the chain and preventing the test suite from running.
 */
public class PlatformCheckHandler extends PreTestCheckHandler {
    public static final Logger logger = LoggerFactory.getLogger(PlatformCheckHandler.class.getSimpleName());

    @Override
    protected boolean performCheck(BookingApiFacade apiFacade) {
        logger.info("Checking if the platform is macOS...");
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            logger.info("Platform check passed! Running on macOS.");
            return true;
        } else {
            logger.warn("Platform check failed! This suite is designed to run only on macOS.");
            return false;
        }
    }
}
