package com.heroku.app;

import com.heroku.app.api.auth.BasicAuthStrategy;
import com.heroku.app.api.auth.TokenAuthStrategy;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.model.Booking;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.getStatusCode(), 200);
        Booking updatedBooking = response.getBookingDetails();
        Assert.assertEquals(updatedBooking.getFirstname(), "Jane");
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
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.getStatusCode(), 200);
        Booking updatedBooking = response.getBookingDetails();
        Assert.assertEquals(updatedBooking.getFirstname(), "Jane");
    }

    @Test
    public void testUpdateBookingMissingAuth() {
        createBooking();
        api.setAuthStrategy(null);
        booking.setFirstname("Jane");
        BookingResponse response = api.updateBooking(bookingId, booking);
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.getStatusCode(), 403);
    }

    @Test
    public void testUpdateBookingNonExistent() {
        createBooking();
        api.setAuthStrategy(new TokenAuthStrategy());
        booking.setFirstname("Jane");
        BookingResponse response = api.updateBooking(999999, booking);
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.getStatusCode(), 404);
    }
}
