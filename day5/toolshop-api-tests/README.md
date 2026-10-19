# toolshop-api-tests

API tests for the Toolshop clean API: https://api.practicesoftwaretesting.com (Swagger docs: https://api.practicesoftwaretesting.com/docs).

Java 21, Maven, REST Assured 6.2.0 and JUnit 5.13.4. Every test checks real content (a field value, a list or an error message), not only the status code.

## Run

From this folder:

    mvn test

## Tests

All 6 tests are in `src/test/java/ToolshopApiTest.java`. The Jira key is at the start of each `@DisplayName`. The Jira story is TF40-70.

| Jira | Test method | What it checks |
|---|---|---|
| TF40-73 | `brandsReturnsNonEmptyListWithNames` | `GET /brands` gives 200 and a plain list. The list is not empty and every brand has a name. |
| TF40-74 | `unknownProductIdReturns404` | `GET /products/{id}` with a random UUID (it cannot exist) gives 404 and the message "Requested item not found". |
| TF40-75 | `searchReturnsOnlyMatchingProducts` | `GET /products/search?q=pliers` gives 200. The `data` list is not empty and every name contains "pliers", ignoring case. |
| TF40-76 | `productsHaveNameAndPrice` | `GET /products` gives 200. The `data` list is not empty. No product is missing a name or has a missing price or a price of 0 or less. |
| TF40-77 | `validLoginReturnsAccessToken` | `POST /users/login` with the demo customer gives 200 and an `access_token` that is not empty and has the shape of a JWT (3 parts). |
| TF40-78 | `loginWithUnknownEmailIsRejected` | `POST /users/login` with an email that does not exist gives 401, the error "Unauthorized" and no `access_token`. |

## Notes

- No product ID is hardcoded. The unknown ID is a random UUID.
- The unknown email test uses an email that does not exist. The seeded demo account is only used for the valid login test, because repeated failed logins can lock it.
- The tests call the live API, so they need internet. A shared public API can be slow or give a one-off error: I saw one `500` on the unknown ID test during development, and it did not come back in 40 more tries.
- The Toolshop API answers `GET /brands` with a plain list, but `GET /products` and `GET /products/search` answer with an object where the products are in `data`.
- `REST Assured` is declared before JUnit in the `pom.xml`, as the REST Assured Getting Started page says, so the right Hamcrest version is used.
