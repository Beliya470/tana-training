import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {

    private static final String LOGIN_URL = "https://practicesoftwaretesting.com/auth/login";
    private static final String REGISTER_URL = "https://api.practicesoftwaretesting.com/users/register";

    // my own user, made fresh for each run (the shop data is shared and resets)
    private static final String PASSWORD = "Str0ng!Pass#2026";
    private static String email;

    private WebDriver driver;
    private WebDriverWait wait;

    // the data-test locators found with Chrome DevTools on the login page
    private final By emailField = By.cssSelector("[data-test='email']");
    private final By passwordField = By.cssSelector("[data-test='password']");
    private final By loginButton = By.cssSelector("[data-test='login-submit']");
    private final By loginError = By.cssSelector("[data-test='login-error']");
    private final By pageTitle = By.cssSelector("[data-test='page-title']");

    // register a new user through the API, so the valid login test does not depend on a shared account
    @BeforeAll
    static void registerUser() throws Exception {
        email = "fellow+" + System.currentTimeMillis() + "@test.com";
        String body = "{\"first_name\":\"Anne\",\"last_name\":\"Tester\",\"email\":\"" + email
                + "\",\"password\":\"" + PASSWORD + "\",\"dob\":\"1990-05-15\"}";

        HttpRequest request = HttpRequest.newBuilder(URI.create(REGISTER_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response.statusCode() == 201, "Register gave " + response.statusCode() + ": " + response.body());
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("TF40-81: Valid login with a registered user lands on the account page")
    void validLoginLandsOnAccountPage() {
        driver.get(LOGIN_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(PASSWORD);
        driver.findElement(loginButton).click();

        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        assertTrue(title.getText().contains("My account"), "The title was: " + title.getText());
    }

    @Test
    @DisplayName("TF40-82: Login with an unknown email shows an error message")
    void loginWithUnknownEmailShowsError() {
        driver.get(LOGIN_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        driver.findElement(emailField).sendKeys("nobody.unknown@example.com");
        driver.findElement(passwordField).sendKeys("wrong-password-1");
        driver.findElement(loginButton).click();

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(loginError));
        assertTrue(error.isDisplayed());
        assertTrue(error.getText().contains("Invalid email or password"), "The error was: " + error.getText());
    }
}
