package com.heroku.app.api;


import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.model.Booking;

import java.util.Map;

/**
 * Defines the interface for Booking API operations.
 * <p>
 * Used as a contract to support flexible implementation and decorator-based extensions.
 */
public interface BookingApi {
    BookingResponse getBooking(int id);
    BookingResponse deleteBooking(int id);
    BookingResponse updateBooking(int id, Booking booking);
    BookingResponse createBooking(Booking booking);
    BookingResponse partialUpdateBooking(int id, Map<String, Object> updates);
    BookingResponse getBookingIds(Map<String, String> queryParams);
}
