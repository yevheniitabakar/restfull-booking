package com.heroku.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.heroku.app.api.auth.BasicAuthStrategy;
import com.heroku.app.api.auth.NoAuthStrategy;
import com.heroku.app.api.auth.TokenAuthStrategy;
import com.heroku.app.api.builder.BookingRequestFactory;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.base.TemplateBaseBookingTest;
import com.heroku.app.model.Booking;
import com.heroku.app.model.BookingResponseWrapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PartialUpdateBookingTest extends TemplateBaseBookingTest {

    @Override
    protected void setup() {
        booking = BookingRequestFactory.createStandardBooking();
    }

    @Override
    protected BookingResponse performAction() {
        BookingResponse response = api.createBooking(booking);
        bookingId = response.getBody().as(BookingResponseWrapper.class).getBookingid();
        return response;
    }

    @Override
    protected void validateResponse(BookingResponse response) {
        assertTrue(response.isSuccessful());
    }

    @Test
    @Tag("Smoke")
    public void testPartialUpdateBookingWithTokenAuth() {
        api.setAuthStrategy(new TokenAuthStrategy());
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstname", "Jane");
        BookingResponse response = loggedApi.partialUpdateBooking(bookingId, updates);
        assertTrue(response.isSuccessful());
        assertEquals(200, response.getStatusCode());
        Booking updatedBooking = response.getBookingDetails();
        assertEquals("Jane", updatedBooking.getFirstname());
    }

    @Test
    public void testPartialUpdateBookingWithBasicAuth() {
        api.setAuthStrategy(new BasicAuthStrategy("admin", "password123"));
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstname", "Jane");
        BookingResponse response = loggedApi.partialUpdateBooking(bookingId, updates);
        assertTrue(response.isSuccessful());
        assertEquals(200, response.getStatusCode());
        Booking updatedBooking = response.getBookingDetails();
        assertEquals("Jane", updatedBooking.getFirstname());
    }

    @Test
    public void testPartialUpdateBookingMissingAuth() {
        api.setAuthStrategy(new NoAuthStrategy());
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstname", "Jane");
        BookingResponse response = loggedApi.partialUpdateBooking(bookingId, updates);
        assertFalse(response.isSuccessful());
        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void testPartialUpdateBookingNonExistent() {
        api.setAuthStrategy(new TokenAuthStrategy());
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstname", "Jane");
        BookingResponse response = loggedApi.partialUpdateBooking(999999, updates);
        assertFalse(response.isSuccessful());
        assertEquals(405, response.getStatusCode());
    }
}
