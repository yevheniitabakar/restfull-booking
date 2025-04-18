package com.heroku.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.heroku.app.api.builder.BookingRequestFactory;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.base.BaseBookingTest;
import com.heroku.app.model.Booking;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CreateBookingTests extends BaseBookingTest {
    private Booking booking;


    @Test
    @Tag("Smoke")
    public void shouldCreateBookingSuccessfully() {
        //TODO Add logic to execute base create booking test
    }

    @Test
    public void testCreateBookingSchemaValidation() {
        booking = BookingRequestFactory.createStandardBooking();
        BookingResponse response = loggedApi.createBooking(booking);

        assertTrue(response.isSuccessful());
        response.getBody()
                .then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("booking-schema.json"));
    }

    @Test
    public void testCreateBookingEmptyBody() {
        booking = BookingRequestFactory.createEmptyBooking();
        BookingResponse response = loggedApi.createBooking(booking);

        assertFalse(response.isSuccessful());
        assertEquals(500, response.getStatusCode());
    }
}
