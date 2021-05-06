package de.beosign.quarkustest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class GreetingResourceTest {
    @Inject
    GreetingService greetingService;

    @BeforeEach
    void beforeEach() {

    }

    @AfterAll
    static void afterAll() {
        // System.out.println("RR" + greetingService.find(4711));
    }

    @Test
    @TestTransaction
    public void testGreetingGetEndpoint() {
        System.out.println("DEL1? " + greetingService.find(4711));

        given()
                .when()
                .get("/greetings/4711")
                .then()
                .statusCode(200)
                .body(containsString("Test4711"));
    }

    @Test
    @TestTransaction
    public void testGreetingPostEndpoint() {
        System.out.println("DEL1? " + greetingService.find(4711));

        given()
                .when().body("{\"message\": \"my msg\"}").contentType(ContentType.JSON).accept(ContentType.JSON)
                .post("/greetings")
                .then()
                .statusCode(200)
                .body(containsString("my msg"));
    }

    @Test
    @TestTransaction
    public void testGreetingDeleteEndpoint() {
        System.out.println("DEL1? " + greetingService.find(4711));

        given()
                .when()
                .delete("/greetings/4711")
                .then()
                .statusCode(200);

        System.out.println("DEL2? " + greetingService.find(4711));
    }

}
