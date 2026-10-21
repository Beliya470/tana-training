# toolshop-api-tests

API tests for the Toolshop clean API: https://api.practicesoftwaretesting.com (Swagger docs: https://api.practicesoftwaretesting.com/docs).

Java 21, Maven, REST Assured 6.2.0 and JUnit 5.13.4. Every test checks real content (a field value, a list or an error message), not only the status code.

## Run

From this folder:

    mvn test

To use another API (for example the with-bugs one):

    mvn test -Dbase.uri=https://api-with-bugs.practicesoftwaretesting.com

## Structure

| File | What it is |
|---|---|
| `BaseApiTest` | Sets `RestAssured.baseURI` once in `@BeforeAll`. It reads the system property `base.uri` and uses the clean API when it is not set. |
| `ToolshopApiTest` | The tests. It extends `BaseApiTest`. It registers a new user in its own `@BeforeAll` and keeps the token. |
| `LoginRequest` | The body of `POST /users/login` (email, password). |
| `RegisterRequest` | The body of `POST /users/register`. |
| `Product` | A product read from `GET /products/{id}` with `.as(Product.class)`. |

## Tests

All 8 tests are in `src/test/java/ToolshopApiTest.java`. The Jira key is at the start of each `@DisplayName`. The Jira story is TF40-70.

| Jira | Test method | What it checks |
|---|---|---|
| TF40-73 | `brandsReturnsNonEmptyListWithNames` | `GET /brands` gives 200 and a plain list. The list is not empty and every brand has a name. |
| TF40-74 | `unknownProductIdReturns404` | `GET /products/{id}` with a random UUID (it cannot exist) gives 404 and the message "Requested item not found". |
| TF40-75 | `searchReturnsOnlyMatchingProducts` | `GET /products/search?q=pliers` gives 200. The `data` list is not empty and every name contains "pliers", ignoring case. |
| TF40-76 | `productsHaveNameAndPrice` | `GET /products` gives 200. The `data` list is not empty. No product is missing a name or has a missing price or a price of 0 or less. |
| TF40-77 | `validLoginReturnsAccessToken` | `POST /users/login` with the demo customer gives 200 and an `access_token` that is not empty and has the shape of a JWT (3 parts). |
| (no key) | `productByIdIsReadAsProductObject` | Takes an id from `GET /products`, reads `GET /products/{id}` as a `Product` and checks id, name and price. |
| (no key) | `meReturnsMyRegisteredUser` | `GET /users/me` with the token gives 200 and the email of the registered user. |
| TF40-78 | `loginWithUnknownEmailIsRejected` | `POST /users/login` with an email that does not exist gives 401, the error "Unauthorized" and no `access_token`. |

## Notes

- No product ID is hardcoded. The unknown ID is a random UUID. The API also reseeds its data: the IDs of brands and products were different in the evening from the afternoon of the same day (the names, prices and structure stayed the same).
- The unknown email test uses an email that does not exist. No test uses the seeded demo account. `@BeforeAll` registers a new user with an email that has a timestamp, so each run has its own user. The seeded account is shared with everyone on the internet: it worked in the morning and gave 401 later on the same day, with no change in my code.
- The tests call the live API, so they need internet. A shared public API can be slow or give a one-off error: I saw one `500` on the unknown ID test during development, and it did not come back in 40 more tries.
- The Toolshop API answers `GET /brands` with a plain list, but `GET /products` and `GET /products/search` answer with an object where the products are in `data`.
- `REST Assured` is declared before JUnit in the `pom.xml`, as the REST Assured Getting Started page says, so the right Hamcrest version is used.
