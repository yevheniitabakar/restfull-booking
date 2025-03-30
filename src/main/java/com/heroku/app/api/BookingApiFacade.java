package com.heroku.app.api;

import static io.restassured.RestAssured.given;

import com.heroku.app.api.auth.AuthStrategy;
import com.heroku.app.api.response.BookingResponse;
import com.heroku.app.api.response.BookingResponseAdapter;
import com.heroku.app.config.RestClientConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Setter;
import com.heroku.app.model.Booking;

import java.util.Map;

/**
 * Implements the Facade design pattern to provide a simplified interface for interacting with the RESTful Booker API.
 * <p>
 * The Facade pattern hides the complexity of low-level RestAssured API calls and authentication handling, offering
 * a high-level interface for performing CRUD operations (e.g., create, get, update, delete bookings). This class
 * centralizes API interactions, making the test framework easier to use and maintain.
 * </p>
 */
public class BookingApiFacade {
    private final RestClientConfig clientConfig = RestClientConfig.getInstance();
    @Setter
    private AuthStrategy authStrategy;

    public BookingResponse healthCheck() {
        Response response = given().get("/ping");

        return new BookingResponseAdapter(response);
    }

    public BookingResponse createBooking(Booking booking) {
        RequestSpecification spec = given()
                .contentType("application/json")
                .body(booking);
        if (authStrategy != null) {
            authStrategy.applyAuth(spec);
        }
        Response response = spec.post("/booking");

        return new BookingResponseAdapter(response);
    }

    public BookingResponse getBookingIds(Map<String, String> queryParams) {
        RequestSpecification spec = given();
        if (queryParams != null) {
            spec.queryParams(queryParams);
        }
        Response response = spec.get("/booking");

        return new BookingResponseAdapter(response);
    }

    public BookingResponse getBooking(int id) {
        Response response = given().get("/booking/" + id);

        return new BookingResponseAdapter(response);
    }

    public BookingResponse updateBooking(int id, Booking booking) {
        RequestSpecification spec = given().body(booking);
        if (authStrategy != null) {
            authStrategy.applyAuth(spec);
        }
        Response response = spec.put("/booking/" + id);

        return new BookingResponseAdapter(response);
    }

    public BookingResponse partialUpdateBooking(int id, Map<String, Object> updates) {
        RequestSpecification spec = given().body(updates);
        if (authStrategy != null) {
            authStrategy.applyAuth(spec);
        }
        Response response = spec.patch("/booking/" + id);
        return new BookingResponseAdapter(response);
    }

    public BookingResponse deleteBooking(int id) {
        RequestSpecification spec = given();
        if (authStrategy != null) {
            authStrategy.applyAuth(spec);
        }
        Response response = spec.delete("/booking/" + id);

        return new BookingResponseAdapter(response);
    }
}
