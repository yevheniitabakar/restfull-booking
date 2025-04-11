package com.heroku.app.hooks;

import com.heroku.app.api.BookingApiFacade;
import com.heroku.app.checks.PreTestCheckInitializer;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PreTestCheckListener implements TestExecutionListener {
    public static final Logger logger = LoggerFactory.getLogger(PreTestCheckListener.class.getSimpleName());
    private static boolean checksDone = false;

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        if (!checksDone) {
            printBanner("[Framework Init] Running Pre-Test Checks...");

            BookingApiFacade apiFacade = new BookingApiFacade();
            PreTestCheckInitializer.runChecks(apiFacade);
            checksDone = true;

            printBanner("[Framework Init] Pre-Test Checks Completed.");
            printBanner("TEST EXECUTION STARTS");
        }
    }

    private void printBanner(String message) {
        String line = "=".repeat(90);
        logger.info(line);
        logger.info(message);
        logger.info("{}\n", line);
    }
}
