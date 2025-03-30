package com.heroku.app.factory;

import com.heroku.app.model.Booking;
import com.heroku.app.model.BookingDates;

/**
 * Concrete implementation of the Factory Method design pattern for creating Booking request payloads.
 * <p>
 * This class extends {@link RequestFactory} and provides a specific implementation for creating a {@link Booking}
 * object, which is used as the payload for API requests (e.g., POST /booking). The Factory Method pattern allows
 * for easy extension to support different types of request payloads in the future.
 * </p>
 */
public class BookingRequestFactory extends RequestFactory{
    @Override
    public Booking createRequest() {
        return new Booking(
                "John",
                "Doe",
                100,
                true,
                new BookingDates("2025-04-01", "2025-04-05"),
                "Breakfast");
    }
}
