package com.heroku.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.heroku.app.api.auth.BasicAuthStrategy;
import com.heroku.app.api.auth.TokenAuthStrategy;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.base.BaseBookingTest;
import org.junit.jupiter.api.Test;

public class DeleteBookingTests extends BaseBookingTest {

    @Override
    protected void setup() {
        createBooking(); // Use the helper method to create a booking
        api.setAuthStrategy(new TokenAuthStrategy());
    }

    @Override
    protected BookingResponse performAction() {
        return api.deleteBooking(bookingId);
    }

    @Override
    protected void validateResponse(BookingResponse response) {
        assertTrue(response.isSuccessful());
        assertEquals(201, response.getStatusCode()); // DELETE returns 201 "Created" in RESTful Booker

        // Verify the booking is deleted by attempting to get it
        BookingResponse getResponse = api.getBooking(bookingId);
        assertEquals(404, getResponse.getStatusCode()); // Should return 404 Not Found
    }

    @Test
    public void testDeleteBookingWithTokenAuth() {
        executeTest(); // Use the template method for TokenAuthStrategy
    }

    @Test
    public void testDeleteBookingWithBasicAuth() {
        createBooking();
        api.setAuthStrategy(new BasicAuthStrategy("admin", "password123"));
        BookingResponse deleteResponse = api.deleteBooking(bookingId);

        assertTrue(deleteResponse.isSuccessful());
        assertEquals(deleteResponse.getStatusCode(), 201);
    }

    @Test
    public void testDeleteBookingMissingId() {
        createBooking();
        api.setAuthStrategy(new TokenAuthStrategy());
        BookingResponse deleteResponse = api.deleteBooking(999999);

        assertFalse(deleteResponse.isSuccessful());
        assertEquals(405, deleteResponse.getStatusCode());
    }

    @Test
    public void testDeleteBookingMissingAuth() {
        createBooking();
        api.setAuthStrategy(null);
        BookingResponse deleteResponse = api.deleteBooking(bookingId);

        assertFalse(deleteResponse.isSuccessful());
        assertEquals(403, deleteResponse.getStatusCode());
    }
}
