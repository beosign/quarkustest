package de.beosign.quarkustest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import javax.inject.Inject;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(PersonResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonResourceTest {
    @Inject
    PersonRepository personRepository;

    @Test
    @TestTransaction
    @Order(1)
    public void testPersonGetEndpoint() {
        given()
                .when()
                .get("/1001")
                .then()
                .statusCode(200)
                .body(containsString("Homer"));
    }

    @Test
    @TestTransaction
    @Order(2)
    public void testPersonDeleteEndpoint() {
        given()
                .when()
                .delete("/1005")
                .then()
                .statusCode(200);

    }

    @Test
    @TestTransaction
    @Order(3)
    public void testPersonPostEndpoint() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person();
        person.setAge(2);
        person.setFirstName("Maggie");
        person.setLastName("Simpson");
        String body = objectMapper.writeValueAsString(person);
        given()
                .when().body(body).contentType(ContentType.JSON).accept(ContentType.JSON)
                .post()
                .then()
                .statusCode(200)
                .body(allOf(containsString("Maggie"), containsString("Simpson"), containsString("2")));
    }

}
