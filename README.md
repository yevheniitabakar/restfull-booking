# Restful-Booking Automation Framework

This is a Java-based API test automation project designed for [https://restful-booker.herokuapp.com](https://restful-booker.herokuapp.com), focusing on **proficient usage of Gang of Four (GoF) design patterns** in a real-world test framework.

---

## 🎯 Project Goals

- ✅ Demonstrate clean architecture and maintainable design
- ✅ Apply classic GoF patterns in relevant testing scenarios
- ✅ Provide dynamic logging, pre-test validations, and flexible test execution

---
## 🧠 Implemented Design Patterns

| Pattern                 | Usage Context                                                                 |
|-------------------------|-------------------------------------------------------------------------------|
| **Factory**             | `BookingRequestFactory` - creates different types of booking objects         |
| **Builder**             | `CustomBookingRequestBuilder`, `DefaultBookingRequestBuilder` - construct test data step-by-step |
| **Template Method**     | `TemplateBaseBookingTest` - defines skeleton of API test execution flow      |
| **Strategy**            | `AuthStrategy` interface with `TokenAuth`, `BasicAuth`, and `NoAuth` classes |
| **Chain of Responsibility** | `PreTestCheckHandler` → `HealthCheckHandler`, `PlatformCheckHandler`         |
| **Adapter**             | `BookingResponseAdapter` and `BookingIdsResponseAdapter` for standardizing responses |
| **Singleton**           | `RestClientConfig` - manages a single instance of RestAssured config         |
| **Decorator**           | `LoggingBookingApiDecorator` - adds logging around API calls without altering core logic |

---

## 🚀 Running Test Suites

Use the following Gradle command to run specific test suites:

```
./gradlew test --tests "com.heroku.app.suites.RegressionSuite"
```

For smoke tests:
```
./gradlew test --tests "com.heroku.app.suites.SmokeSuite"
```

You can also run all tests:
```
./gradlew test
```

> Note: Logging is fully configured via `logback.xml`. Logs will appear in the console and `logs/framework.log`.

---

## 📁 Project Structure (Important Highlights)
```
src
├── main
│   └── java
│       └── com.heroku.app
│           ├── api         ← Authentication, request builders, and facade
│           ├── checks      ← Chain of Responsibility handlers
│           ├── config      ← Singleton setup for RestAssured
│           ├── factory     ← Factory classes for bookings
│           ├── model       ← POJOs for requests and responses
│           └── response    ← Adapters for API responses
├── test
│   └── java
│       └── com.heroku.app
│           ├── base        ← Template pattern base classes
│           ├── hooks       ← JUnit 5 extensions for pre-checks and logging
│           ├── suites      ← Test suites (Regression, Smoke)
│           └── tests       ← Actual test classes for each endpoint
```

---

## 📦 Dependencies

- **JUnit 5 (Jupiter)**
- **RestAssured**
- **AssertJ**
- **Lombok**
- **Logback (SLF4J)**

---

## 📝 Notes

- `PreTestCheckListener` ensures all test conditions (like health check and platform validation) are satisfied before running any test.
- Logging includes **test start/end markers**, request/response details, and test-specific metadata via MDC.
- Designed with extensibility and clean separation of concerns in mind.

---

## 📌 Future Improvements

- Add parallel execution via JUnit 5 configuration  
- Add retry mechanism via custom extension  
- Add reporting (e.g., Allure or custom HTML)

---
## 👨‍💻 Author
Yevhenii Tabakar  
📬 [[LinkedIn](www.linkedin.com/in/yevhenii-tabakar-485329204)]  
🧪 Passionate about clean test architecture and automation design patterns.
