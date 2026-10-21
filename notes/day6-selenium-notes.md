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
- What I do differently, as the task says: I use IntelliJ-style Maven (a `pom.xml`) instead of adding Selenium JAR files by hand, and I do not write `System.setProperty(...)` for the driver. Selenium Manager (part of Selenium 4) finds my Chrome and downloads the matching driver by itself.
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
