import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class ToolshopApiTest extends BaseApiTest {

    // a JWT is three parts made of letters, digits, - and _, split by dots
    private static final String JWT_PATTERN = "^[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+$";

    // GET /brands gives a plain list (a JSON array), not an object with a "data" list
    @Test
    @DisplayName("TF40-73: GET /brands returns a non-empty list and each item has a name")
    void brandsReturnsNonEmptyListWithNames() {
        given()
        .when()
            .get("/brands")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))                // the list is not empty
            .body("name", everyItem(not(emptyOrNullString()))); // every brand has a name
    }

    // {id} is a path parameter. A random UUID can never be a real product ID, so nothing is hardcoded.
    @Test
    @DisplayName("TF40-74: GET /products/{id} with an unknown ID returns 404")
    void unknownProductIdReturns404() {
        String unknownId = UUID.randomUUID().toString();

        given()
            .pathParam("id", unknownId)
        .when()
            .get("/products/{id}")
        .then()
            .statusCode(404)
            .body("message", equalTo("Requested item not found"));
    }

    // q is a query parameter. The search gives a page object, and the products are in its "data" list.
    @Test
    @DisplayName("TF40-75: Product search with a query parameter returns only matching products")
    void searchReturnsOnlyMatchingProducts() {
        String term = "pliers";

        given()
            .queryParam("q", term)
        .when()
            .get("/products/search")
        .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0))                                // something was found
            .body("data.name", everyItem(containsStringIgnoringCase(term)));    // and every name matches the term
    }

    // GET /products gives a page object: the products are in the "data" list
    @Test
    @DisplayName("TF40-76: GET /products returns products with name and price present")
    void productsHaveNameAndPrice() {
        given()
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0))                                 // the page has products
            .body("data.findAll { !it.name }", empty())                           // no product without a name
            .body("data.findAll { it.price == null || it.price <= 0 }", empty()); // no product without a price above 0
    }

    // the demo customer from the Swagger docs. Only the valid login test uses this account.
    @Test
    @DisplayName("TF40-77: Valid login returns an access_token")
    void validLoginReturnsAccessToken() {
        LoginRequest body = new LoginRequest("customer@practicesoftwaretesting.com", "welcome01");

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/users/login")
        .then()
            .statusCode(200)
            .body("access_token", not(emptyOrNullString()))   // the token is at the top level of the body
            .body("access_token", matchesPattern(JWT_PATTERN)); // and it is a real JWT: 3 parts split by dots
    }

    // an email that does not exist. Never use the seeded account here: repeated failed logins can lock it.
    @Test
    @DisplayName("TF40-78: Login with an unknown email is rejected")
    void loginWithUnknownEmailIsRejected() {
        LoginRequest body = new LoginRequest("nobody.unknown@example.com", "wrong-password-1");

        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/users/login")
        .then()
            .statusCode(401)
            .body("error", equalTo("Unauthorized"))
            .body("access_token", nullValue());              // no token is given
    }
}
