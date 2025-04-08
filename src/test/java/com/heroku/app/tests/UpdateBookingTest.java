package com.heroku.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.heroku.app.api.auth.BasicAuthStrategy;
import com.heroku.app.api.auth.TokenAuthStrategy;
import com.heroku.app.api.builder.BookingRequestFactory;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.base.TemplateBaseBookingTest;
import com.heroku.app.model.Booking;
import com.heroku.app.model.BookingDates;
import com.heroku.app.model.BookingResponseWrapper;
import org.junit.jupiter.api.Test;


public class UpdateBookingTest extends TemplateBaseBookingTest {

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
    public void testUpdateBookingWithTokenAuth() {
    }

    @Test
    public void testUpdateBookingWithBasicAuth() {

        api.setAuthStrategy(new BasicAuthStrategy("admin", "password123"));
        Booking updatedBookingRequest = BookingRequestFactory.createCustomBooking()
                .setFirstName("James")
                .setLastName("Brown")
                .setTotalPrice(300)
                .setDepositPaid(true)
                .setBookingDates(new BookingDates("2025-04-04", "2025-04-08"))
                .setAdditionalNeeds("Balcony")
                .buildRequest();
        BookingResponse response = api.updateBooking(bookingId, updatedBookingRequest);
        assertTrue(response.isSuccessful());
        assertEquals(200, response.getStatusCode());
        Booking updatedBooking = response.getBookingDetails();
        assertEquals("James", updatedBooking.getFirstname());
    }

    @Test
    public void testUpdateBookingMissingAuth() {
        api.setAuthStrategy(null);
        booking.setFirstname("Jane");
        BookingResponse response = api.updateBooking(bookingId, booking);
        assertFalse(response.isSuccessful());
        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void testUpdateBookingNonExistent() {
        api.setAuthStrategy(new TokenAuthStrategy());
        Booking updatedBookingRequest = BookingRequestFactory.createCustomBooking()
                .setFirstName("James")
                .buildRequest();
        BookingResponse response = api.updateBooking(bookingId, updatedBookingRequest);
        assertFalse(response.isSuccessful());
        assertEquals(400, response.getStatusCode());
    }
}
