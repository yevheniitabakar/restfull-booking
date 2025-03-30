package com.heroku.app;

import com.heroku.app.api.auth.BasicAuthStrategy;
import com.heroku.app.api.auth.TokenAuthStrategy;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.model.Booking;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class PartialUpdateBookingTest extends BaseBookingTest{
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
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.getStatusCode(), 200);
        Booking updatedBooking = response.getBookingDetails();
        Assert.assertEquals(updatedBooking.getFirstname(), "Jane");
        Assert.assertEquals(updatedBooking.getLastname(), booking.getLastname()); // Unchanged field
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
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.getStatusCode(), 200);
        Booking updatedBooking = response.getBookingDetails();
        Assert.assertEquals(updatedBooking.getFirstname(), "Jane");
    }

    @Test
    public void testPartialUpdateBookingMissingAuth() {
        createBooking();
        api.setAuthStrategy(null);
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstname", "Jane");
        BookingResponse response = api.partialUpdateBooking(bookingId, updates);
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.getStatusCode(), 403);
    }

    @Test
    public void testPartialUpdateBookingNonExistent() {
        createBooking();
        api.setAuthStrategy(new TokenAuthStrategy());
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstname", "Jane");
        BookingResponse response = api.partialUpdateBooking(999999, updates);
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.getStatusCode(), 404);
    }
}
