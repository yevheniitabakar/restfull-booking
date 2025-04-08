package com.heroku.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.heroku.app.api.builder.BookingRequestFactory;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.base.BaseBookingTest;
import com.heroku.app.model.BookingDates;
import com.heroku.app.model.BookingResponseWrapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class GetBookingIdsTest extends BaseBookingTest {
    private String customFirstName = "CustomFirstName";

    @Test
    public void testGetBookingIdsPositive() {
    }

    @Test
    public void testGetBookingIdsWithFilter() {
        booking = BookingRequestFactory.createCustomBooking()
                .setFirstName(customFirstName)
                .setLastName("customLastName")
                .setTotalPrice(111)
                .setDepositPaid(true)
                .setBookingDates(new BookingDates("2025-01-01", "2025-01-06"))
                .buildRequest();
        BookingResponse createResponse = api.createBooking(booking);
        bookingId = createResponse.getBody().as(BookingResponseWrapper.class).getBookingid();

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("firstname", customFirstName);
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
