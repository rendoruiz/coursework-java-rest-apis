package dmit2015.rendoruiz.assignment02.resource;

import dmit2015.rendoruiz.assignment02.entity.OscarReview;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains the microshed integration test for the REST API of the OscarReview class.
 *
 * @author Rendo Ruiz
 * @version 2021.03.02
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicroShedTest
public class OscarReviewResourceMicroshedTestingIT {

    @Container
    public static ApplicationContainer app = new ApplicationContainer()
            .withAppContextRoot("/dmit2015-assignment02-rendoruiz")
            .withReadinessPath("/dmit2015-assignment02-rendoruiz/webapi/OscarReviews")
            .withStartupTimeout(Duration.ofSeconds(180));

    String testDataResourceLocation;

    @Order(1)
    @Test
    void shouldListAll() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/webapi/OscarReviews")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();

        // Create a new Jsonb instance using the default JsonbBuilder implementation
        Jsonb jsonb = JsonbBuilder.create();
        List<OscarReview> oscarReviews = jsonb.fromJson(jsonBody, new ArrayList<OscarReview>(){}.getClass().getGenericSuperclass());
        assertEquals(3, oscarReviews.size());
    }

    @Order(2)
    @Test
    void shouldCreate() {
        OscarReview newOscarReview = new OscarReview();
        newOscarReview.setCategory("film");
        newOscarReview.setNominee("Microshed Test");
        newOscarReview.setReview("Create Microshed Integration Test");
        newOscarReview.setUsername("microshed");

        // Create a new Jsonb instance using the default JsonbBuilder implementation
        Jsonb jsonb = JsonbBuilder.create();
        String jsonBody = jsonb.toJson(newOscarReview);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/webapi/OscarReviews")
                .then()
                .statusCode(201)
                .extract()
                .response();
        testDataResourceLocation = response.getHeader("location");
    }

    @Order(3)
    @Test
    void shouldFineOne() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();
        // Create a new Jsonb instance using the default JsonbBuilder implementation
        Jsonb jsonb = JsonbBuilder.create();
        OscarReview existingOscarReview = jsonb.fromJson(jsonBody, OscarReview.class);
        assertNotNull(existingOscarReview);
        assertEquals("film", existingOscarReview.getCategory());
        assertEquals("Microshed Test", existingOscarReview.getNominee());
        assertEquals("Create Microshed Integration Test", existingOscarReview.getReview());
        assertEquals("microshed", existingOscarReview.getUsername());
    }

    @Order(4)
    @Test
    void shouldUpdate() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();
        // http://json-b.net/docs/user-guide.html
        // Create a new Jsonb instance using the default JsonbBuilder implementation
        Jsonb jsonb = JsonbBuilder.create();
        OscarReview existingOscarReview = jsonb.fromJson(jsonBody, OscarReview.class);
        assertNotNull(existingOscarReview);
        existingOscarReview.setReview("Microshed Integration Test");
        existingOscarReview.setNominee("Update Microshed Integration Test");

        String jsonRequestBody = jsonb.toJson(existingOscarReview);
        given()
                .contentType(ContentType.JSON)
                .body(jsonRequestBody)
                .when()
                .put(testDataResourceLocation)
                .then()
                .statusCode(204);
    }

    @Order(5)
    @Test
    void shouldDelete() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(testDataResourceLocation)
                .then()
                .statusCode(204);
    }

}