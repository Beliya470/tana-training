# toolshop-ui-tests

UI tests for the Toolshop shop: https://practicesoftwaretesting.com

Java 21, Maven, Selenium 4.51.0 and JUnit 5.13.4. Chrome must be installed. Selenium Manager downloads the matching browser driver by itself, so no driver setup is needed.

## Run

From this folder:

    mvn test

## Tests

| Class | What it checks |
|---|---|
| `FirstTest` | Chrome opens by itself, goes to the home page and the page title contains "Practice Software Testing". The browser is started in `@BeforeEach` and closed with `driver.quit()` in `@AfterEach`. |
