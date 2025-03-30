package com.heroku.app.tests;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.heroku.app.api.auth.NoAuthStrategy;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.api.response.BookingResponseAdapter;
import com.heroku.app.base.BaseBookingTest;
import com.heroku.app.factory.BookingRequestFactory;
import io.restassured.module.jsv.JsonSchemaValidator;
import com.heroku.app.model.Booking;
import org.junit.jupiter.api.Test;

public class CreateBookingTests extends BaseBookingTest {
    private Booking booking;

    @Override
    protected void setup() {
        booking = new BookingRequestFactory().createRequest();
        api.setAuthStrategy(new NoAuthStrategy());
    }

    @Override
    protected BookingResponse performAction() {
        return api.createBooking(booking);
    }

    @Override
    protected void validateResponse(BookingResponse response) {
        assertTrue(response.isSuccessful());
        assertEquals(200, response.getStatusCode());
        Booking createdBooking = response.getBookingDetails();
        assertEquals(booking.getFirstname(), createdBooking.getFirstname());
        assertEquals(booking.getLastname(), createdBooking.getLastname());
    }

    @Test
    public void testCreateBooking() {
        executeTest();
    }

    @Test
    public void testCreateBookingSchemaValidation() {
        booking = new BookingRequestFactory().createRequest();
        api.setAuthStrategy(new NoAuthStrategy());
        BookingResponse response = api.createBooking(booking);

        assertTrue(response.isSuccessful());
        response.getBody()
                .then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("booking-schema.json"));
    }

//    @Test
//    public void testCreateBookingWrongMethod() {
//        BookingResponse response = new BookingResponseAdapter(given().get("/booking"));
//
//        assertFalse(response.isSuccessful());
//        assertEquals(405, response.getStatusCode());
//    }

    @Test
    public void testCreateBookingEmptyBody() {
        BookingResponse response = new BookingResponseAdapter(given().body("").post("/booking"));

        assertFalse(response.isSuccessful());
        assertEquals(500, response.getStatusCode());
    }
}
