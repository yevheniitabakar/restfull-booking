package com.heroku.app.api.builder;

import com.heroku.app.model.Booking;
import com.heroku.app.model.BookingDates;


/**
 * Concrete implementation of the {@link AbstractBookingRequestBuilder} that provides a fully
 * populated and valid {@link Booking} object with default values.
 * <p>
 * Used for tests that require a standard, valid booking payload without custom configuration.
 * Follows the Template Method pattern to ensure a consistent setup while hiding internal field logic.
 *
 * @see AbstractBookingRequestBuilder
 */
public class DefaultBookingRequestBuilder  extends AbstractBookingRequestBuilder {

    @Override
    protected void applyCustomizations(Booking booking) {
        booking.setFirstname("John");
        booking.setLastname("Doe");
        booking.setTotalprice(123);
        booking.setDepositpaid(true);
        booking.setBookingdates(new BookingDates("2024-01-01", "2024-01-10"));
        booking.setAdditionalneeds("Breakfast");
    }
}
