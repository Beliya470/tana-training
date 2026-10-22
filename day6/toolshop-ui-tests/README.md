# toolshop-ui-tests

UI tests for the Toolshop shop: https://practicesoftwaretesting.com

Java 21, Maven, Selenium 4.51.0 and JUnit 5.13.4. Chrome must be installed. Selenium Manager downloads the matching browser driver by itself, so no driver setup is needed.

## Run

From this folder:

    mvn test

Without a browser window (this is how the CI server runs it):

    mvn test -Dheadless=true

## Tests

| Class | What it checks |
|---|---|
| `FirstTest` | Chrome opens by itself, goes to the home page and the page title contains "Practice Software Testing". The browser is started in `@BeforeEach` and closed with `driver.quit()` in `@AfterEach`. |
| `LoginTest` | Two login tests on https://practicesoftwaretesting.com/auth/login. TF40-81: a registered user logs in and lands on the account page (the page title contains "My account"). TF40-82: an unknown email shows the error "Invalid email or password". Every wait is an explicit `WebDriverWait` of 10 seconds, with no `Thread.sleep`. The locators are `data-test` CSS selectors. |
| `LoginPage`, `AccountPage` | Page objects (not tests). `LoginPage` keeps the locators of the login page as private fields and has `open()`, `loginAs(email, password)` and `getErrorText()`. `AccountPage` has `getTitleText()` for the account page. The assertions stay in `LoginTest`. |
| `DriverFactory` | Makes the Chrome driver for every test. With `-Dheadless=true` it starts Chrome without a window (`--headless=new`, window size 1920x1080). |

## Notes

- The valid login uses a new user that `@BeforeAll` registers through the API (`POST /users/register`, an email with the time in it). No test uses a seeded or shared account, because the shop data is shared with everyone and resets.
- Each `@DisplayName` starts with its Jira key. The story is TF40-79, the precondition is TF40-80.
