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
import com.heroku.app.model.BookingResponseWrapper;
import org.junit.jupiter.api.Test;

public class DeleteBookingTests extends TemplateBaseBookingTest {

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
    public void testDeleteBookingWithTokenAuth() {
        api.setAuthStrategy(new TokenAuthStrategy());
        BookingResponse deleteResponse = api.deleteBooking(bookingId);

        assertTrue(deleteResponse.isSuccessful());
    }

    @Test
    public void testDeleteBookingWithBasicAuth() {
        api.setAuthStrategy(new BasicAuthStrategy("admin", "password123"));
        BookingResponse deleteResponse = api.deleteBooking(bookingId);

        assertTrue(deleteResponse.isSuccessful());
    }

    @Test
    public void testDeleteBookingMissingId() {
        api.setAuthStrategy(new TokenAuthStrategy());
        BookingResponse deleteResponse = api.deleteBooking(999999);

        assertFalse(deleteResponse.isSuccessful());
        assertEquals(405, deleteResponse.getStatusCode());
    }

    @Test
    public void testDeleteBookingMissingAuth() {
        api.setAuthStrategy(new NoAuthStrategy());
        BookingResponse deleteResponse = api.deleteBooking(bookingId);

        assertFalse(deleteResponse.isSuccessful());
        assertEquals(403, deleteResponse.getStatusCode());
    }
}
