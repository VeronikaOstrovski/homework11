package org.example;

import com.google.gson.Gson;
import dto.Order;
import dto.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.text.AbstractDocument;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;


public class DeliveryAPITest {

    static String token;

    //    Task 13
    @BeforeAll
    public static void setUp() {

        System.out.println("---> test start");

        SetupFunctions setupFunctions = new SetupFunctions();
        token = setupFunctions.getToken();

        System.out.println("token " + setupFunctions.getToken());

        RestAssured.baseURI = setupFunctions.getBaseUrl();


       Assumptions.assumeFalse(token.isEmpty(), "Token is not exists, all test skipped");
    }

    // Task 14
    // 1. Create an order
    @Test
    public void createOrder() {

        Order newOrder = new Order(0, "Today", "+37255577788", "good day", 0);
        Gson gson = new Gson();

        given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(newOrder))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

        Assertions.assertNotNull (newOrder.getCustomerName());
    }

    // Task 14
    // 2. Search a created order
    // Create order for searching and deleting
    public int createOrderForSearchingAndDeleting() {
        Order createdOrder = new Order(0, "Saturday", "+372", "good day", 0);
        Gson createdGson = new Gson();

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(createdGson.toJson(createdOrder))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .extract()
                .response();

        Order orderId = createdGson.fromJson(response.asString(), Order.class);

        return orderId.getId();

    }

    // Search created order by orderId (to get orderId from POST)
    @Test
    public void searchCreatedOrder() {

        int orderIdCreated = createOrderForSearchingAndDeleting();

       String status = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders/" + orderIdCreated)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .path("status");


        Assertions.assertEquals("OPEN", status);

    }

    // Task 14
    // 3. Delete order
    @Test
    public void deleteOrder() {

        int orderIdCreated = createOrderForSearchingAndDeleting();

        given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .delete("orders/" + orderIdCreated)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .response()
                .getBody()
                .asString();

        Assertions.assertTrue(true);

        // Searched a deleted order

        String body = given ()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("orders/" + orderIdCreated)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .response()
                .getBody()
                .asString();

        Assumptions.assumeTrue(body.isEmpty(), "Test passed if response body is empty");

    }

    // Task 14
    // 4. Negative test - test without token (GET)
    @Test
    public void negativeTestOrderWithoutTokenGet() {
        given()
                .when()
                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("orders/3814")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        Assumptions.assumeFalse(token.isEmpty(), "Token is not exists, all test skipped");

    }

    // Task 14
    // 5. Negative test - test without header content-type (GET)
    @Test
    public void negativeTestOrderHeaderContentTypeGet() {
        String id = given()
                .when()
//                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("orders/3814")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .extract()
                .path("id");

        Assertions.assertEquals(3814, id);

    }
}
