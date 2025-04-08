package com.heroku.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.heroku.app.api.builder.BookingRequestFactory;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.base.BaseBookingTest;
import com.heroku.app.model.Booking;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

public class CreateBookingTests extends BaseBookingTest {
    private Booking booking;

    @Test
    public void shouldCreateBookingSuccessfully() {
    }

    @Test
    public void testCreateBookingSchemaValidation() {
        booking = BookingRequestFactory.createStandardBooking();
        BookingResponse response = api.createBooking(booking);

        assertTrue(response.isSuccessful());
        response.getBody()
                .then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("booking-schema.json"));
    }

    @Test
    public void testCreateBookingEmptyBody() {
        booking = BookingRequestFactory.createEmptyBooking();
        BookingResponse response = api.createBooking(booking);

        assertFalse(response.isSuccessful());
        assertEquals(500, response.getStatusCode());
    }
}
