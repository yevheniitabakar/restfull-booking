package com.heroku.app;

import com.heroku.app.api.response.BookingResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.getStatusCode(), 200);
        // Validate that the response contains the created booking ID
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains(String.valueOf(bookingId)));
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
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.getStatusCode(), 200);
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains(String.valueOf(bookingId)));
    }

    @Test
    public void testGetBookingIdsInvalidQueryParam() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("invalidParam", "value");
        BookingResponse response = api.getBookingIds(queryParams);
        Assert.assertTrue(response.isSuccessful()); // API ignores invalid params
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
