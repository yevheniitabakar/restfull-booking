package com.heroku.app.base;

import com.heroku.app.api.BookingApi;
import com.heroku.app.api.BookingApiFacade;
import com.heroku.app.api.LoggingBookingApiDecorator;
import com.heroku.app.api.auth.NoAuthStrategy;
import com.heroku.app.api.builder.BookingRequestFactory;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.hooks.TestLoggingExtension;
import com.heroku.app.model.Booking;
import com.heroku.app.model.BookingResponseWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shared base class for all booking-related test suites.
 * <p>
 * Provides access to Booking API (via Decorator-wrapped implementation), shared test data,
 * and a method to create bookings for setup purposes.
 */
@ExtendWith(TestLoggingExtension.class)
public abstract class BaseBookingTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseBookingTest.class.getSimpleName());
    protected static BookingApiFacade api;
    protected static BookingApi loggedApi;

    protected Booking booking;
    protected int bookingId;

    @BeforeAll
    public static void initializeApiClients() {
        logger.info("Initializing API clients...");
        api = new BookingApiFacade();
        loggedApi = new LoggingBookingApiDecorator(api);
    }

    protected void createBooking() {
        booking = BookingRequestFactory.createStandardBooking();
        api.setAuthStrategy(new NoAuthStrategy());
        BookingResponse createResponse = api.createBooking(booking);
        if (!createResponse.isSuccessful()) {
            throw new RuntimeException("Failed to create booking: " + createResponse.getStatusCode());
        }
        bookingId = createResponse.getBody().as(BookingResponseWrapper.class).getBookingid();
    }
}
