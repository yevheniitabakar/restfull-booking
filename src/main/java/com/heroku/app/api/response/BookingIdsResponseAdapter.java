package com.heroku.app.api.response;

import com.heroku.app.model.Booking;
import com.heroku.app.model.BookingIdEntry;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.List;

public class BookingIdsResponseAdapter implements BookingResponse {
    private final Response response;

    public BookingIdsResponseAdapter(Response response) {
        this.response = response;
    }

    public List<BookingIdEntry> getIds() {
        return response.as(new TypeRef<List<BookingIdEntry>>() {});
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    @Override
    public Response getBody() {
        return response;
    }

    @Override
    public Booking getBookingDetails() {
        return null;
    }

    public boolean isSuccessful() {
        return response.getStatusCode() == 200;
    }
}
