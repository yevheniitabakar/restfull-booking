package com.heroku.app.checks;


import com.heroku.app.api.BookingApiFacade;

/**
 * Abstract base class for the Chain of Responsibility design pattern, used to perform pre-test checks.
 * <p>
 * The Chain of Responsibility pattern allows multiple handlers to process a request in a chain, where each handler
 * can either handle the request or pass it to the next handler. In this framework, this class defines a chain of
 * pre-test checks (e.g., API health check) that must pass before running the test suite. If a check fails, the chain
 * stops, and the tests are skipped.
 */
public abstract class PreTestCheckHandler {
    private PreTestCheckHandler nextHandler;

    public void setNext(PreTestCheckHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void check(BookingApiFacade apiFacade) {
        if (performCheck(apiFacade)) {
            if (nextHandler != null) {
                nextHandler.check(apiFacade);
            }
        } else {
            throw new RuntimeException(this.getClass().getSimpleName() + " failed! Aborting tests.");
        }
    }

    protected abstract boolean performCheck(BookingApiFacade apiFacade);
}
