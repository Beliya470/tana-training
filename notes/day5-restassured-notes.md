# Day 5 notes: REST Assured

From the Udemy course "REST Assured API Test automation for Beginners" (introduction lectures so far), the REST Assured Getting Started page, the Usage guide and the JUnit overview page.

## The course (introduction lectures)
- Three parts: (1) introduction and a little theory, (2) creating tests for every RESTful method (GET, POST, PUT, PATCH, DELETE) and using JSON path to read values from a response, (3) advanced features: request specification, headers and cookies, path and query parameters, and serialize and deserialize of requests and responses.
- REST Assured is a Java library. The course uses Java, Apache Maven for the project, TestNG as the test framework and Eclipse. The task for today says that if the course uses TestNG, the REST Assured code is the same and I write my tests with JUnit 5. I use VS Code and the terminal.
- The goal: be able to automate REST APIs without a tool like Postman or SoapUI.
- Tips from the lecture: watch in the highest resolution, use the resources attached to each lecture, read the Q&A section when something does not work, and do not only watch. Type the code myself and search online (StackOverflow) for errors. The instructor keeps the code of each lecture in a GitHub project, to compare with mine if I have an error.

## Web services, SOAP and REST
- A web service is software that two devices on a network use to talk to each other. The two sides can be written in different languages.
- There are two main types, SOAP and REST.

| | SOAP | REST |
|---|---|---|
| What it is | A standardized protocol (Simple Object Access Protocol). It sends messages using other protocols such as HTTP and SMTP. | An architectural style (Representational State Transfer), not a protocol. |
| Rules | Strict rules and advanced security. More complex, needs more resources, so it can be slower. | More flexible, with fewer rules, so usually faster. |
| Formats | XML only | HTML, JSON, XML, even plain text |
| HTTP methods | Every request goes through HTTP POST | GET, POST, PUT, DELETE and PATCH for the different operations |
| Used for | Enterprise services with high security and complex transactions (example: PayPal's public API) | Most public APIs (example: Google Maps) |

- REST Assured has "rest" in its name, but it can also test SOAP APIs.

## Setting up the project (the Maven lecture) and what I changed
| In the lecture | What I did |
|---|---|
| Create a Maven project in Eclipse (JDK 11, not older than 1.8) | A Maven project made by hand in VS Code, JDK 21 |
| Add the Maven compiler plugin and the Maven Surefire plugin to `pom.xml` | Same. Surefire 3.2.5 (3.x is needed for JUnit 5) |
| Add the TestNG dependency (7.1) and remove `<scope>test</scope>` | JUnit 5 (`junit-jupiter` 5.13.4) instead of TestNG, because the task says to write the tests with JUnit 5. I kept `test` scope. |
| Add the REST Assured dependency | `io.rest-assured:rest-assured:6.2.0` (the current version on its Getting Started page) |
| Right click, Maven, Update project | In the terminal Maven downloads the libraries when I run `mvn test` |
| TestNG plugin for Eclipse to run tests | Not needed. I run `mvn test` |

Dependencies are jar files. Maven downloads them for me, so I do not download jars by hand.

## REST Assured Getting Started page
- Dependency: `io.rest-assured:rest-assured:6.2.0`, scope `test`. It includes JsonPath and XmlPath.
- Put `rest-assured` BEFORE the JUnit dependency in `pom.xml`, so the right version of Hamcrest is used.
- With Java 9 or newer and a problem with split packages, use `rest-assured-all` instead.
- Static imports that make the code short: `io.restassured.RestAssured.*`, `io.restassured.matcher.RestAssuredMatchers.*` and `org.hamcrest.Matchers.*`.
- The JUnit overview page I was given says JUnit 6.1.3 needs Java 17 or higher. The task says JUnit 5, so I use 5.13.4.

## given(), when() and then()
A REST Assured test reads like a test case. The three words are the three parts of it:

| Word | What it does | In my test |
|---|---|---|
| `given()` | Sets up the request: parameters, headers, body, content type, authentication. It sends nothing yet. | `given().queryParam("q", "pliers")` |
| `when()` | Sends the request: the HTTP method and the path (GET, POST...). | `.when().get("/products/search")` |
| `then()` | Checks the response: the status code, the body and the headers. If a check is wrong the test fails. | `.then().statusCode(200).body("data.name", everyItem(containsStringIgnoringCase("pliers")))` |

It is the same as Arrange, Act, Assert: given = arrange, when = act, then = assert.

## From the Usage guide
- Body checks use Groovy GPath: `body("lotto.lottoId", equalTo(5))` reads a field of the response and compares it. A path like `data.name` gives the list of all the names. `size()` gives the size of a list. `findAll { ... }` filters a list, for example `store.book.findAll { it.price < 10 }.title`.
- Hamcrest matchers check the value: `equalTo`, `hasItems`, `hasSize`, `everyItem`, `containsStringIgnoringCase`, `not`, `empty`, `nullValue`.
- Parameters: `queryParam("name", "value")` (goes after the `?`), `pathParam("id", value)` with `get("/products/{id}")`, and unnamed path parameters like `post("/reserve/{hotelId}/{roomNumber}", "My Hotel", 23)`.
- A JSON body: `contentType(ContentType.JSON).body(...)` then `post(...)`. A body made from a Map or an object needs a JSON library (Jackson or Gson) on the classpath. A JSON text does not, so I use a text block.
- Reading a value out of a response: `extract().path("...")` or `extract().response()`.
- Defaults: `RestAssured.baseURI = "..."` and `RestAssured.basePath = "..."` set the address once for all requests.
- Response time: `time(lessThan(2000L))`, in milliseconds. I did not use it on the Toolshop API because a shared public API can be slow some days (one of my runs took 76 seconds once).

## What I decided for my tests
- Every test checks the status code and real content.
- JUnit 5 `@DisplayName` starts with the Jira key.
- No product ID is hardcoded.
- `@BeforeAll` sets the base URI once.
