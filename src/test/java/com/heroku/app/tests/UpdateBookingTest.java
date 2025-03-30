package com.heroku.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.heroku.app.api.auth.BasicAuthStrategy;
import com.heroku.app.api.auth.TokenAuthStrategy;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.base.BaseBookingTest;
import com.heroku.app.model.Booking;
import org.junit.jupiter.api.Test;


public class UpdateBookingTest extends BaseBookingTest {
    @Override
    protected void setup() {
        createBooking();
        api.setAuthStrategy(new TokenAuthStrategy());
    }

    @Override
    protected BookingResponse performAction() {
        booking.setFirstname("Jane");
        return api.updateBooking(bookingId, booking);
    }

    @Override
    protected void validateResponse(BookingResponse response) {
        assertTrue(response.isSuccessful());
        assertEquals(200, response.getStatusCode());
        Booking updatedBooking = response.getBookingDetails();
        assertEquals("Jane", updatedBooking.getFirstname());
    }

    @Test
    public void testUpdateBookingWithTokenAuth() {
        executeTest();
    }

    @Test
    public void testUpdateBookingWithBasicAuth() {
        createBooking();
        api.setAuthStrategy(new BasicAuthStrategy("admin", "password123"));
        booking.setFirstname("Jane");
        BookingResponse response = api.updateBooking(bookingId, booking);
        assertTrue(response.isSuccessful());
        assertEquals(200, response.getStatusCode());
        Booking updatedBooking = response.getBookingDetails();
        assertEquals("Jane", updatedBooking.getFirstname());
    }

    @Test
    public void testUpdateBookingMissingAuth() {
        createBooking();
        api.setAuthStrategy(null);
        booking.setFirstname("Jane");
        BookingResponse response = api.updateBooking(bookingId, booking);
        assertFalse(response.isSuccessful());
        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void testUpdateBookingNonExistent() {
        createBooking();
        api.setAuthStrategy(new TokenAuthStrategy());
        booking.setFirstname("Jane");
        BookingResponse response = api.updateBooking(999999, booking);
        assertFalse(response.isSuccessful());
        assertEquals(404, response.getStatusCode());
    }
}
