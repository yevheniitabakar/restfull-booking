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

import java.util.HashMap;
import java.util.Map;

public class PartialUpdateBookingTest extends BaseBookingTest {
    @Override
    protected void setup() {
        createBooking();
        api.setAuthStrategy(new TokenAuthStrategy());
    }

    @Override
    protected BookingResponse performAction() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstname", "Jane");
        return api.partialUpdateBooking(bookingId, updates);
    }

    @Override
    protected void validateResponse(BookingResponse response) {
        assertTrue(response.isSuccessful());
        assertEquals(200, response.getStatusCode());
        Booking updatedBooking = response.getBookingDetails();
        assertEquals("Jane", updatedBooking.getFirstname());
        assertEquals(updatedBooking.getLastname(), booking.getLastname()); // Unchanged field
    }

    @Test
    public void testPartialUpdateBookingWithTokenAuth() {
        executeTest();
    }

    @Test
    public void testPartialUpdateBookingWithBasicAuth() {
        createBooking();
        api.setAuthStrategy(new BasicAuthStrategy("admin", "password123"));
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstname", "Jane");
        BookingResponse response = api.partialUpdateBooking(bookingId, updates);
        assertTrue(response.isSuccessful());
        assertEquals(200, response.getStatusCode());
        Booking updatedBooking = response.getBookingDetails();
        assertEquals("Jane", updatedBooking.getFirstname());
    }

    @Test
    public void testPartialUpdateBookingMissingAuth() {
        createBooking();
        api.setAuthStrategy(null);
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstname", "Jane");
        BookingResponse response = api.partialUpdateBooking(bookingId, updates);
        assertFalse(response.isSuccessful());
        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void testPartialUpdateBookingNonExistent() {
        createBooking();
        api.setAuthStrategy(new TokenAuthStrategy());
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstname", "Jane");
        BookingResponse response = api.partialUpdateBooking(999999, updates);
        assertFalse(response.isSuccessful());
        assertEquals(404, response.getStatusCode());
    }
}
