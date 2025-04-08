package com.heroku.app.api.response;

import io.restassured.response.Response;
import com.heroku.app.model.Booking;
import com.heroku.app.model.BookingResponseWrapper;


/**
 * Implements the Adapter design pattern to adapt a raw API response into a test-friendly format.
 * <p>
 * The Adapter pattern converts the interface of a raw RestAssured {@link Response} into a more convenient
 * interface defined by {@link BookingResponse}. This class deserializes the response into a structured format
 * (e.g., a {@link Booking} object) and provides utility methods for test assertions, such as checking the status
 * code and success status.
 * </p>
 */
public class BookingResponseAdapter implements BookingResponse {
    private final Response response;
    private BookingResponseWrapper wrapper;
    private Booking directBooking;

    public BookingResponseAdapter(Response response) {
        this.response = response;

        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            String contentType = response.getContentType();

            if (contentType != null && contentType.contains("application/json")) {
                String json = response.getBody().asString();

                if (json.contains("bookingid")) {
                    this.wrapper = response.as(BookingResponseWrapper.class);
                } else {
                    this.directBooking = response.as(Booking.class);
                }
            } else {
                System.err.println("⚠️ Skipping JSON parsing: unsupported content type: " + contentType);
            }
        }
    }

    @Override
    public int getStatusCode() {
        return response.getStatusCode();
    }

    @Override
    public boolean isSuccessful() {
        return response.getStatusCode() == 200 | response.getStatusCode() == 201;
    }

    @Override
    public Response getBody() {
        return response;
    }

    public Booking getBookingDetails() {
        if (wrapper != null) {
            return wrapper.getBooking();
        } else if (directBooking != null) {
            return directBooking;
        }
        return null;
    }
}
