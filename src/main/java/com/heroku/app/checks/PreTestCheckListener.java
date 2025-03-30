package com.heroku.app.checks;

import com.heroku.app.api.BookingApiFacade;
import org.testng.IExecutionListener;

public class PreTestCheckListener implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        System.out.println("PreTestCheckListener onExecutionStart -------------->>>>>>>>>>>>>");
        BookingApiFacade apiFacade = new BookingApiFacade();
        PreTestCheckHandler health = new HealthCheckHandler();
        try {
            health.check(apiFacade);
        } catch (RuntimeException e) {
            throw new RuntimeException("Pre-test checks failed, skipping suite: " + e.getMessage(), e);
        }
    }

    @Override
    public void onExecutionFinish() {
        IExecutionListener.super.onExecutionFinish();
    }
}
