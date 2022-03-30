package org.acme;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.engine.TestExecutionResult;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.http.HttpHeaders;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(RegistrationResource.class)
public class RegistrationResourceTest {

    @Test
    @Order(1)
    public void shouldRegisterAUSER() {
        final User user = new User();
        user.userName = "Alex";
        user.email = "asotobu@gmail.com";

        given().body(user)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post("/registration")
                .then().statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:8081/registration/1");
    }

    @Test
    @Order(2)
    public void shouldFindUserByUsername(){
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .get("/registration/{username}", "Alex")
                .then()
                .statusCode(200)
                .body("username", is("Alex"))
                .body("password", notNullValue());
    }

}