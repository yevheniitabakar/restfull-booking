package com.heroku.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingResponseWrapper {
    private int bookingid;
    private Booking booking;
}
