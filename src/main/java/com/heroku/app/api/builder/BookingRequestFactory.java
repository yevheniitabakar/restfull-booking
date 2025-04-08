package com.heroku.app.api.builder;

import com.heroku.app.model.Booking;

/**
 * Implements the Factory design pattern to centralize the creation of reusable {@link Booking} test objects.
 * <p>
 * This factory provides pre-configured {@link Booking} instances through builders, allowing tests
 * to easily request default, custom, or empty payloads without duplicating setup logic.
 * <p>
 * Returns instances of {@link CustomBookingRequestBuilder} and {@link DefaultBookingRequestBuilder}
 * to support both fluent configuration and Template Method construction styles.
 */
public class BookingRequestFactory{

    public static Booking createStandardBooking() {
        return new DefaultBookingRequestBuilder().buildRequest();
    }

    public static CustomBookingRequestBuilder createCustomBooking() {
        return new CustomBookingRequestBuilder();
    }

    public static Booking createEmptyBooking() {
        return new CustomBookingRequestBuilder().buildRequest();
    }

    public static CustomBookingRequestBuilder createInvalidFullNameBooking() {
        return new CustomBookingRequestBuilder()
                .setFirstName("")
                .setLastName("");
    }
}
