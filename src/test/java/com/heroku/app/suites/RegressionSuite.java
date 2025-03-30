package com.heroku.app.suites;


import com.heroku.app.tests.CreateBookingTests;
import com.heroku.app.tests.DeleteBookingTests;
import com.heroku.app.tests.GetBookingIdsTest;
import com.heroku.app.tests.PartialUpdateBookingTest;
import com.heroku.app.tests.UpdateBookingTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CreateBookingTests.class,
        UpdateBookingTest.class,
        DeleteBookingTests.class,
        GetBookingIdsTest.class,
        PartialUpdateBookingTest.class
})
public class RegressionSuite {
}
