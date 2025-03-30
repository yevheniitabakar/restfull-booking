package com.heroku.app.base;

import com.heroku.app.api.BookingApiFacade;
import com.heroku.app.api.auth.NoAuthStrategy;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.checks.PreTestCheckInitializer;
import com.heroku.app.factory.BookingRequestFactory;
import com.heroku.app.model.BookingResponseWrapper;
import com.heroku.app.model.Booking;
import org.junit.jupiter.api.BeforeAll;

/**
 * Abstract base class implementing the Template Method design pattern for standardizing test execution flow.
 * <p>
 * The Template Method pattern defines the skeleton of an algorithm (setup, perform action, validate response) in a
 * method ({@link #executeTest()}), allowing subclasses to customize specific steps without changing the overall
 * structure. This class provides a reusable test execution flow for API tests, with helper methods for common setup
 * tasks (e.g., creating a booking), while allowing flexibility for custom test scenarios.
 */
public abstract class BaseBookingTest {
    protected BookingApiFacade api = new BookingApiFacade();
    protected Booking booking;
    protected int bookingId;

    protected abstract void setup();
    protected abstract BookingResponse performAction();
    protected abstract void validateResponse(BookingResponse response);

    @BeforeAll
    public static void runPreTestChecks() {
        BookingApiFacade apiFacade = new BookingApiFacade();
        PreTestCheckInitializer.runChecks(apiFacade);
    }

    public void executeTest() {
        setup();
        BookingResponse response = performAction();
        validateResponse(response);
    }

    protected void createBooking() {
        booking = new BookingRequestFactory().createRequest();
        api.setAuthStrategy(new NoAuthStrategy());
        BookingResponse createResponse = api.createBooking(booking);
        if (!createResponse.isSuccessful()) {
            throw new RuntimeException("Failed to create booking: " + createResponse.getStatusCode());
        }
        bookingId = createResponse.getBody().as(BookingResponseWrapper.class).getBookingid();
    }
}
