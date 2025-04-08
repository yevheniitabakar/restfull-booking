package com.heroku.app.base;

import com.heroku.app.api.response.BookingResponse;
import org.junit.jupiter.api.BeforeEach;


/**
 * Implements the Template Method design pattern to define a fixed structure for test execution.
 * <p>
 * This class enforces a specific test lifecycle: {@code setup()} → {@code performAction()} →
 * {@code validateResponse()}, which is automatically executed before each test via JUnit's {@code @BeforeEach}.
 * <p>
 * Subclasses can override each step to define their own flow, while keeping the structure consistent.
 * <p>
 * Extend this class only for test suites where all test methods follow a common flow.
 */
public abstract class TemplateBaseBookingTest extends BaseBookingTest {
    protected abstract BookingResponse performAction();
    protected abstract void validateResponse(BookingResponse response);
    protected void setup() {
    }

    @BeforeEach
    public void runTestFlow() {
        setup();
        BookingResponse response = performAction();
        validateResponse(response);
    }
}
