package com.heroku.app.factory;

/**
 * Abstract base class for the Factory Method design pattern, used to create request payloads for API calls.
 * <p>
 * The Factory Method pattern defines an interface for creating objects but allows subclasses to decide which
 * class to instantiate. In this framework, this class provides a contract for creating request payloads (e.g., a
 * Booking object for the RESTful Booker API), enabling flexible and extensible payload generation.
 * </p>
 */
public abstract class RequestFactory {
    public abstract Object createRequest();
}
