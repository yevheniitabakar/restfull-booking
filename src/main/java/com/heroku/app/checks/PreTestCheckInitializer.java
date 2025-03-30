package com.heroku.app.checks;

import com.heroku.app.api.BookingApiFacade;


/**
 * Concrete handler in the Chain of Responsibility design pattern, responsible for performing a platform check.
 * <p>
 * This class extends {@link PreTestCheckHandler} and implements a demonstration-only check to simulate
 * a scenario where tests are restricted to run exclusively on macOS. It verifies the operating system using
 * the {@code os.name} system property.
 * <p>
 * If the check determines that the platform is not macOS, it throws a {@link RuntimeException},
 * halting the test execution and preventing further checks from running.
 * <p>
 * This check is for educational purposes and is not based on actual system restrictions.
 */
public class PreTestCheckInitializer {
    public static void runChecks(BookingApiFacade apiFacade) {
        PlatformCheckHandler platformCheckHandler = new PlatformCheckHandler();
        HealthCheckHandler healthCheckHandler = new HealthCheckHandler();

        platformCheckHandler.setNext(healthCheckHandler);
        platformCheckHandler.check(apiFacade);
    }
}
