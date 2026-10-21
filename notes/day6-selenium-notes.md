# Day 6 afternoon notes: Selenium WebDriver

## Selenium setup

**Selenium Introduction (Udemy, 7:58)**
- Selenium is an open-source framework to automate web browsers and web applications. It is free: no licence to buy. A Selenium program controls a browser like a user would: open a site, type in a field, click a button, pick from a drop-down, and check the result.
- It works with many languages: Java, Python, C#, JavaScript, Ruby and Kotlin. The course (and my work) uses Java, because Java is common in companies and common for Selenium.
- It works with many browsers: Chrome, Firefox, Edge, Safari. This matters because an app must work in every browser. It also has a very large community, so most problems already have an answer online.
- Short history: 2004 Jason Huggins made the JavaScript Test Runner at ThoughtWorks. 2006 WebDriver was made. 2008 Selenium RC and WebDriver joined into Selenium 2. 2016 Selenium 3. 2021 Selenium 4, which follows the W3C WebDriver standard. I use Selenium 4. I do not need to remember the years.
- What Selenium can do: open a URL, click links and buttons, type text, pick from drop-downs, use check boxes, handle elements that appear late (it can wait for them), and check the page title, the text, the URL and if a field is visible.
- What Selenium cannot do: it does not automate desktop programs (for example the Windows calculator), and it is not a load test tool. For 10,000 users at once I would use JMeter or Gatling. Selenium is a web browser tool.
- **What WebDriver does between my code and the browser:** my Java code does not talk to Chrome directly. It calls WebDriver commands (get, findElement, click). WebDriver turns each one into a standard request and sends it to a small program called the browser driver (chromedriver for Chrome). The driver tells the real browser what to do and sends the result back to my code.

**Launch Google Chrome via Selenium WebDriver (Udemy, 6:35)**
- This lecture only installs Eclipse: Eclipse is an IDE (a tool to write, run and debug Java code). It is free and not required. Any other IDE is fine because the Selenium code is the same. The instructor downloads the installer for his processor (ARM64 or x86_64; on Windows I can check Settings, System, About, System type), opens it, picks the default workspace and launches it. I did not install Eclipse.
- What I do differently, as the task says: I use a Maven project (a `pom.xml`) instead of adding Selenium JAR files by hand, and I do not write `System.setProperty(...)` for the driver. Selenium Manager (part of Selenium 4) finds my Chrome and downloads the matching driver by itself.
- Launching Chrome is one line: `WebDriver driver = new ChromeDriver();`

**Selenium method to get the title of a web page (Udemy, 5:53)**
- `driver.getTitle()` returns the title of the current page as a `String`. It is the text on the browser tab.
- Use: to check that I am on the page I expect. After going to the login page I read the title and compare it with the expected title. If they match, I am on the right page.
- The steps in the lecture: launch Chrome, `driver.get(url)` to open the home page, then `String title = driver.getTitle();` and print it. The instructor also puts classes in packages to keep the project tidy.

**Selenium docs: Install a Selenium library (Java)**
- For Java, Selenium is added with a build tool. With Maven I add the `selenium-java` dependency (group `org.seleniumhq.selenium`) in `pom.xml`. The docs use a property for the version, `${selenium.version}`. I set it to 4.51.0, the latest on Maven Central today.

**JUnit user guide (what I need from it)**
- JUnit 6 = JUnit Platform + JUnit Jupiter + JUnit Vintage. The Platform starts test frameworks on the JVM and works with IDEs and build tools (Maven too). Jupiter is the model for writing tests (`@Test`, `@BeforeEach`). Vintage runs old JUnit 3 and 4 tests and is deprecated. JUnit needs Java 17 or higher. I use the same JUnit 5.13.4 as on Day 4 and Day 5, with the `junit-jupiter` dependency.

## First Selenium test (project `day6/toolshop-ui-tests`)
- Maven project with JDK 21. Dependencies: `selenium-java` 4.51.0 and `junit-jupiter` 5.13.4.
- `FirstTest`: `new ChromeDriver()` in `@BeforeEach` (a new Chrome for each test) and `driver.quit()` in `@AfterEach`. `quit()` closes all the windows and ends the session. `close()` only closes the current window, so a driver process could stay behind.
- One test opens `https://practicesoftwaretesting.com` and checks that `driver.getTitle()` contains "Practice Software Testing". The real title is "Practice Software Testing - Toolshop - v5.0".
- `mvn test`: Chrome opens and closes by itself, 1 test, green.

## Locators and interacting with elements

**Introduction of Selenium Locators and types (Udemy, 4:05)**
- Launching the browser is only the start. To use a page, Selenium must know which element I mean: a user name box, a login button, a check box, a link. A locator is a way to identify one element (a GUI element) on the page so that Selenium can work with it.
- If Selenium cannot find the element with my locator, the script fails when it runs. So the choice of locator is important: a good locator finds the element easily, a poor one breaks the script.
- Selenium has 8 locator types: ID, Name, Link Text, Partial Link Text, Tag Name, Class Name, CSS Selector and XPath. One can be better than another for a given element. Some are more reliable than others.

**CSS Selector (Udemy, 4:04) and CSS Selector with different attributes (4:21)**
- CSS stands for Cascading Style Sheets. It is made to style pages, but the same selector syntax can find elements. It does the same job as XPath (find an element), with a different and often shorter syntax.
- The lecture lists 7 ways to write a CSS selector: by id, by class, by an attribute, by several attributes, and by an attribute value that contains, starts with or ends with some text. (Tag, id, class and attribute are the basic ones.)
- By attribute: write the tag name, then the attribute name and its value in square brackets, like `button[data-action='apply']`. The lecture uses this when the element has no useful id or class. It checks the selector in the browser (the search box of the Elements tab, Ctrl+F) to see that exactly one element matches, then uses it in code with `driver.findElement(By.cssSelector("..."))` and `.click()`. If one attribute does not make the element unique, use more than one attribute.
- In the Toolshop every important element has a `data-test` attribute, so the selector is short and stable: `[data-test='email']`.

**Text box operations (Udemy, 10:41)**
- With a text box I can: type a value with `sendKeys("text")`, read the value that is already in it with `getAttribute("value")` (the typed text is stored in the `value` attribute, not as visible text), and delete it with `clear()`.
- The steps in the lecture: get the driver, `driver.get(url)`, find the element (by name in the lecture), then act on it. Finding is the first half and acting is the second half. When I need the same element many times I store it in a `WebElement` variable.
- The lecture adds `Thread.sleep(10 seconds)` only to slow the script down so that the typed text can be seen before `clear()` empties the field, because Selenium is very fast. `Thread.sleep` is Java, not Selenium, and it needs a `throws` declaration. I do not use it in my tests (see waits below).

**Button operations (Udemy, 8:50)**
- `click()` clicks the button. `isEnabled()` returns true or false and can be checked before a click. `getText()` returns the visible text of the button (for example "Click me").
- Same pattern: find the element (by id here), keep it in a `WebElement` variable, then call the methods. The page shows a message when the button is clicked, which proves that the click worked.

### Answers

**Why use a data-test CSS locator instead of a long XPath?**
A `data-test` attribute is put on the element on purpose, only for testing, so it has one clear meaning (`[data-test='email']`). It does not depend on where the element is in the page or on how it looks. A long XPath like `/html/body/app-root/div/app-login/div/form/div[1]/input` depends on the whole structure of the page. If a developer adds a `div` or moves the field, the XPath breaks even though the field still works. The CSS locator is also short and easy to read, and I can check it in DevTools in a few seconds.

**What do findElement(), sendKeys() and click() each do?**
- `findElement(By...)` looks for the first element on the page that matches the locator and gives it back as a `WebElement`. If there is none, it throws `NoSuchElementException`.
- `sendKeys("text")` types the text into the element, like a person on the keyboard.
- `click()` clicks the element, like a person with the mouse.

### The login page locators (found with Chrome DevTools on /auth/login)

| Element | data-test value | CSS locator |
|---|---|---|
| Email field | `email` | `[data-test='email']` |
| Password field | `password` | `[data-test='password']` |
| Login button | `login-submit` | `[data-test='login-submit']` |
| Error message (shown after a wrong login) | `login-error` | `[data-test='login-error']` |
| Page title (on the account page after a login) | `page-title` | `[data-test='page-title']` |

I read the first three from the login page in Chrome. The error and the page title only appear after a login, so I read them from the page that my test opens: the error is a `div` with the text "Invalid email or password", and the page title is an `h1` with the text "My account".

## Waits and automating the Toolshop login

**Understanding the Synchronization Usage in Selenium (Udemy, 5:56)**
- Selenium is very fast. A web page takes time to load an element, show a button, get data from the server, show a pop-up or change its content. If Selenium looks for an element before it is there, it throws `NoSuchElementException` and the test stops. This is a synchronization problem between Selenium and the application.
- The lecture compares it to waiting for a friend at the station: the train is late, so I must wait until it arrives, not go home.
- The ways to wait: `Thread.sleep` (it is Java, not Selenium), the implicit wait, the explicit wait and the fluent wait (the last three are Selenium). The practice page has buttons whose message only shows after a few seconds.

**Thread Sleep, why not to use (Udemy, 10:06)**
- `Thread.sleep` pauses the whole program for a fixed time. In the lecture a click is followed by a search for the message. Without a wait it fails with `NoSuchElementException`. With `Thread.sleep(10 seconds)` it works.
- The problem: it always waits all the time. If the element comes after 1 second, the script still sleeps for 10. With 1000 tests and 5 wasted seconds in each, that is 5000 seconds, more than 80 minutes of doing nothing in every run. And if the page is slower than the sleep, the test fails anyway.

**Understanding the need for the Explicit wait (Udemy, 9:22)**
- An explicit wait waits for one condition that I choose, up to a maximum time, and carries on as soon as the condition is true. Examples: an element is visible, a button is clickable, an alert is present.
- The code: `WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));` then `wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("...")))`. It gives back the `WebElement`, so I can read the text or click.
- It keeps trying for up to 10 seconds. The condition list in `ExpectedConditions` is long (alert present, attribute contains, and more).

**Selenium docs, Waiting strategies**
- A race condition between the browser and my code is one of the main causes of flaky tests: sometimes the page is ready first and the test passes, sometimes the code runs first and fails. Elements added by JavaScript, or made visible by a click, may not be there when the next command runs.
- A `sleep` fails when it is too short and makes the run very long when it is too long and used everywhere.
- Implicit wait: one global time for every `findElement`. The default is 0. Do not mix implicit and explicit waits, because the total can be unpredictable (an implicit 10 s with an explicit 15 s could time out after 20 s). I use only explicit waits.
- Explicit wait: a loop that checks a condition until it is true or the time is over (then it gives a timeout error). It also waits for the element to exist. The `FluentWait` class lets me change how often it checks (polling), which errors to ignore and the message.

### Answers

**What is the difference between Thread.sleep(2000) and an explicit wait?**
`Thread.sleep(2000)` always stops for exactly 2 seconds, whatever the page does. If the element is ready after 200 ms I lose 1.8 seconds, and if it needs 3 seconds the test fails. An explicit wait checks a condition again and again, for example "the email field is visible", and goes on at once when it is true. It fails only if the condition is not true in the maximum time (10 seconds in my tests). So it is faster when the page is fast and still works when the page is slow.

**What happens if my code looks for an element that is not on the page yet?**
`driver.findElement(...)` does not wait. It throws `NoSuchElementException` at once and the test fails, although the page is fine and only a little slow. With an explicit wait the code keeps checking until the element is there. If it never shows up in the time limit, the wait throws a `TimeoutException`, which says clearly what was missing.

### The login tests (`LoginTest`)
- Two tests, one for each Jira case: TF40-81 (valid login lands on the account page) and TF40-82 (an unknown email shows an error). The `@DisplayName` of each starts with its Jira key. Story TF40-79 and precondition TF40-80 are in Jira too.
- Each test opens the login page, waits until the email field is visible (`WebDriverWait`, 10 seconds, `visibilityOfElementLocated`), types the email and the password, and clicks Login. The valid test waits for `page-title` and checks that it contains "My account". The invalid test waits for `login-error` and checks that it is shown and says "Invalid email or password". There is no `Thread.sleep` anywhere.
- The valid test uses a user that `@BeforeAll` registers with `POST /users/register` (a new email with the time in it). It uses the Java `HttpClient`, so there is no new library. No test relies on a seeded or shared account.
- Both tests passed 3 runs in a row.
