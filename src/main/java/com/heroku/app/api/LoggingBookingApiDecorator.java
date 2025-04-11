package com.heroku.app.api;


import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.model.Booking;
import com.heroku.app.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Supplier;


/**
 * Implements the Decorator design pattern to wrap {@link BookingApi} with SLF4J-based logging.
 * <p>
 * This allows seamless logging of API request input and output without altering business logic.
 */
public class LoggingBookingApiDecorator implements BookingApi {
    private final BookingApi delegate;
    private static final Logger logger = LoggerFactory.getLogger(LoggingBookingApiDecorator.class.getSimpleName());

    public LoggingBookingApiDecorator(BookingApi delegate) {
        this.delegate = delegate;
        logger.info("Initialized logging booking api decorator");
    }

    private BookingResponse logAndExecute(String action, Object input, Supplier<BookingResponse> request) {
        logger.info("{} called with: {}", action, input != null ? input : "no input");

        BookingResponse response = request.get();
        String rawBody = response.getBody().asString();

        // Pretty-print only if the body looks large or like JSON
        String loggedBody = rawBody.length() > 100 || rawBody.trim().startsWith("{")
                ? JsonUtils.toPrettyJson(rawBody)
                : rawBody;
        logger.info("Response: status={}, body={}\n{}", response.getStatusCode(), System.lineSeparator(), loggedBody);

        return response;
    }

    private BookingResponse logAndExecute(String action, Supplier<BookingResponse> request) {
        return logAndExecute(action, "no input", request);
    }


    @Override
    public BookingResponse getBooking(int id) {
       return logAndExecute("GET Booking", id, () -> delegate.getBooking(id));
    }

    @Override
    public BookingResponse deleteBooking(int id) {
        return logAndExecute("DELETE Booking", id, () -> delegate.deleteBooking(id));
    }

    @Override
    public BookingResponse updateBooking(int id, Booking booking) {
        return logAndExecute("UPDATE Booking", id, () -> delegate.updateBooking(id, booking));
    }

    @Override
    public BookingResponse createBooking(Booking booking) {
        return logAndExecute("POST Booking", () -> delegate.createBooking(booking));
    }

    @Override
    public BookingResponse partialUpdateBooking(int id, Map<String, Object> updates) {
        return logAndExecute("PATCH Booking", id, () -> delegate.partialUpdateBooking(id, updates));
    }

    @Override
    public BookingResponse getBookingIds(Map<String, String> queryParams) {
        return logAndExecute("GET BokingId's", queryParams, () -> delegate.getBookingIds(queryParams));
    }
}
