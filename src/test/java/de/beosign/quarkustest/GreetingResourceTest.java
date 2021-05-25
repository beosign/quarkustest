package de.beosign.quarkustest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import javax.inject.Inject;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GreetingResourceTest {
    @Inject
    GreetingService greetingService;

    @Test
    @TestTransaction
    @Order(1)
    public void testGreetingGetEndpoint() {
        given()
                .when()
                .get("/greetings/4711")
                .then()
                .statusCode(200)
                .body(containsString("Test4711"));
    }

    @Test
    @TestTransaction
    @Order(2)
    public void testGreetingDeleteEndpoint() {
        given()
                .when()
                .delete("/greetings/4711")
                .then()
                .statusCode(200);

        System.out.println("DEL2? " + greetingService.find(4711));
    }

    @Test
    @TestTransaction
    @Order(3)
    public void testGreetingPostEndpoint() {
        given()
                .when().body("{\"message\": \"my msg\"}").contentType(ContentType.JSON).accept(ContentType.JSON)
                .post("/greetings")
                .then()
                .statusCode(200)
                .body(containsString("my msg"));
    }

}
