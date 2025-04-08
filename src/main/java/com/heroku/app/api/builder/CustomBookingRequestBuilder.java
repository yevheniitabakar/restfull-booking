package com.heroku.app.api.builder;

import com.heroku.app.model.Booking;
import com.heroku.app.model.BookingDates;

/**
 * Implements the Builder design pattern for constructing {@link Booking} objects with custom field values.
 * <p>
 * This class extends {@link AbstractBookingRequestBuilder} to leverage the Template Method pattern,
 * while providing a fluent API for setting individual booking fields such as first name, last name, price, and dates.
 * <p>
 * Ideal for tests that require partial or negative input data, as each field can be optionally set or omitted.
 *
 * @see AbstractBookingRequestBuilder
 */
public class CustomBookingRequestBuilder extends AbstractBookingRequestBuilder {

    private String firstName;
    private String lastName;
    private Integer totalPrice;
    private Boolean depositPaid;
    private BookingDates bookingDates;
    private String additionalNeeds;


    public CustomBookingRequestBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomBookingRequestBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomBookingRequestBuilder setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public CustomBookingRequestBuilder setDepositPaid(Boolean depositPaid) {
        this.depositPaid = depositPaid;
        return this;
    }

    public CustomBookingRequestBuilder setBookingDates(BookingDates bookingDates) {
        this.bookingDates = bookingDates;
        return this;
    }

    public CustomBookingRequestBuilder setAdditionalNeeds(String additionalNeeds) {
        this.additionalNeeds = additionalNeeds;
        return this;
    }

    @Override
    protected void applyCustomizations(Booking booking) {
        if (firstName != null) booking.setFirstname(firstName);
        if (lastName != null) booking.setLastname(lastName);
        if (totalPrice != null) booking.setTotalprice(totalPrice);
        if (depositPaid != null) booking.setDepositpaid(depositPaid);
        if (bookingDates != null) booking.setBookingdates(bookingDates);
        if (additionalNeeds != null) booking.setAdditionalneeds(additionalNeeds);
    }
}
