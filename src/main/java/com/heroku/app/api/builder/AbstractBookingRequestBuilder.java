package com.heroku.app.api.builder;

import com.heroku.app.model.Booking;


/**
 * Implements the Template Method design pattern to define a structured flow for building a {@link Booking} object.
 * <p>
 * This abstract builder class defines the common steps for constructing a booking request, such as
 * setting required and optional fields. It provides a {@code buildRequest()} method that executes a fixed sequence
 * of actions while allowing subclasses to override and customize specific parts via {@code applyCustomizations()}.
 * <p>
 * Used in combination with the Builder pattern to support fluent and flexible test data creation.
 *
 * @see CustomBookingRequestBuilder
 * @see DefaultBookingRequestBuilder
 */
public abstract class AbstractBookingRequestBuilder {

    /**
     * Builds the final {@link Booking} object by applying required defaults and any custom logic
     * defined by the subclass via {@link #applyCustomizations(Booking)}.
     *
     * @return fully constructed {@link Booking} instance
     */
    public final Booking buildRequest() {
        Booking booking = new Booking();
        setupDefaultValues(booking);
        applyCustomizations(booking);
        return booking;
    }

    protected void setupDefaultValues(Booking booking) {
        booking.setFirstname("John");
        booking.setLastname("Doe");
        booking.setTotalprice(150);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Breakfast");
    }

    protected abstract void applyCustomizations(Booking booking);
}
