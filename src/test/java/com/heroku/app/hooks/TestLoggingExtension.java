package com.heroku.app.hooks;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


/**
 * JUnit 5 extension that logs the beginning and end of each test method,
 * and sets the test name in MDC for logback to use in logs.
 */
public class TestLoggingExtension implements BeforeEachCallback, AfterEachCallback {
    public static final Logger logger = LoggerFactory.getLogger(TestLoggingExtension.class.getSimpleName());

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        String testName = extensionContext.getDisplayName();
        MDC.put("TestName -> ", testName);

        logger.info("Starting test: -> {}", testName);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        logger.info("Finished test: -> {}", extensionContext.getDisplayName());
        MDC.clear();
    }
}
