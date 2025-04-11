package com.heroku.app.suites;


import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.heroku.app.tests")
@IncludeTags("Smoke")
public class SmokeSuite {
}
