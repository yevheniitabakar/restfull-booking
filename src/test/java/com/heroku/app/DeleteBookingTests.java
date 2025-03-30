package com.heroku.app;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.heroku.app.api.auth.BasicAuthStrategy;
import com.heroku.app.api.auth.TokenAuthStrategy;
import com.heroku.app.api.response.BookingResponse;
import org.testng.annotations.Test;

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
        assertEquals(response.getStatusCode(), 201); // DELETE returns 201 "Created" in RESTful Booker

        // Verify the booking is deleted by attempting to get it
        BookingResponse getResponse = api.getBooking(bookingId);
        assertEquals(getResponse.getStatusCode(), 404); // Should return 404 Not Found
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
        assertEquals(deleteResponse.getStatusCode(), 405);
    }

    @Test
    public void testDeleteBookingMissingAuth() {
        createBooking();
        api.setAuthStrategy(null);
        BookingResponse deleteResponse = api.deleteBooking(bookingId);

        assertFalse(deleteResponse.isSuccessful());
        assertEquals(deleteResponse.getStatusCode(), 403);
    }
}
