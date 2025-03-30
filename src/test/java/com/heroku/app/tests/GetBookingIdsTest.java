package com.heroku.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.base.BaseBookingTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class GetBookingIdsTest extends BaseBookingTest {
    @Override
    protected void setup() {
        createBooking();
    }

    @Override
    protected BookingResponse performAction() {
        return api.getBookingIds(null);
    }

    @Override
    protected void validateResponse(BookingResponse response) {
        assertTrue(response.isSuccessful());
        assertEquals(200, response.getStatusCode());
        // Validate that the response contains the created booking ID
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains(String.valueOf(bookingId)));
    }

    @Test
    public void testGetBookingIdsPositive() {
        executeTest();
    }

    @Test
    public void testGetBookingIdsWithFilter() {
        createBooking();
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("firstname", booking.getFirstname());
        BookingResponse response = api.getBookingIds(queryParams);
        assertTrue(response.isSuccessful());
        assertEquals(200, response.getStatusCode());
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains(String.valueOf(bookingId)));
    }

    @Test
    public void testGetBookingIdsInvalidQueryParam() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("invalidParam", "value");
        BookingResponse response = api.getBookingIds(queryParams);
        assertTrue(response.isSuccessful()); // API ignores invalid params
        assertEquals(200, response.getStatusCode());
    }
}
