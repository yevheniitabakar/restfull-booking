package com.heroku.app.api.response;

import io.restassured.response.Response;
import com.heroku.app.model.Booking;

/**
 * Interface for the Adapter design pattern, defining a contract for adapting API responses into a test-friendly format.
 * <p>
 * The Adapter pattern converts the interface of a class (in this case, a raw RestAssured {@link Response}) into
 * another interface that the test framework expects. This interface provides methods to access booking details,
 * status codes, and the raw response body in a structured way, making it easier to write assertions in tests.
 * </p>
 */
public interface BookingResponse {
    boolean isSuccessful();
    int getStatusCode();
    Response getBody();
    Booking getBookingDetails();
}
