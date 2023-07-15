package org.example;

import com.google.gson.Gson;
import dto.Order;
import dto.User;
import io.restassured.RestAssured;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.text.AbstractDocument;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.CoderResult;
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

        Assertions.assertNotNull(newOrder.getCustomerName());
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

        String body = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .delete("/orders/" + orderIdCreated)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .response()
                .getBody()
                .asString();

        Assertions.assertTrue(true, body);

        // Searched a deleted order

        String bodyIsEmpty = given()
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
                .response()
                .getBody()
                .asString();

        Assumptions.assumeTrue(bodyIsEmpty.isEmpty(), "Test passed if response body is empty");
    }

    // Task 14
    // 4. Negative test - test without token (GET)
    @Test
    public void negativeTestOrderWithoutTokenGet() {

        int orderIdCreated = createOrderForSearchingAndDeleting();

        given()
                .when()
                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders/" + orderIdCreated)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

        //Владимир, где у нас должно отображается это сообщение "Token is not exists, all test skipped"? В логах я его не вижу
        Assumptions.assumeFalse(token.isEmpty(), "Token is not exists, all test skipped");
    }

    // Task 14
    // 5. Negative test - test without header content-type (GET)
    @Test
    public void negativeTestOrderHeaderContentTypeGet() {

        Order orderForNegativeTest = new Order(0, "Wednesday", "+37299988877", "sunny", 0);
        Gson gsonForNegativeTest = new Gson();

        String headerContentType = given()
                .when()
//                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gsonForNegativeTest.toJson(orderForNegativeTest))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE)
                .extract()
                .asString();

        //Здесь такой же вопрос, что и выше. Где оно отображается?
        Assumptions.assumeFalse(headerContentType.isEmpty(), "Content-Type is not exists, all test skipped");
    }

    // Task 15
    // 1.1.Available orders with role student

    @Test
    public void availableOrdersRoleStudent() {

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders/available")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode());
    }

    //1.2.Status orders with role student
    @Test
    public void statusOrderRoleStudent() {

        Order statusOrderRoleStudent = new Order(0, "Tuesday", "+37211122233", "hello", 0);
        Gson gsonStatusOrderRoleStudent = new Gson();

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gsonStatusOrderRoleStudent.toJson(statusOrderRoleStudent))
                .log()
                .all()
                .put("/orders/" + statusOrderRoleStudent.getId() + "/status")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode());
    }

    // 3. Check array orders
    @Test
    public void deleteArrayOrders() {

        Order[] ordersArray = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Order[].class);

        if (ordersArray.length > 0) {

            for (int i = 0; i < ordersArray.length; i++) {

                System.out.println("Deleting order with id: " + ordersArray[i].getId());
                Response response = given()
                        .when()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .log()
                        .all()
                        .delete("/orders/" + ordersArray[i].getId())
                        .then()
                        .log()
                        .all()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .response();

                Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
            }
        }
    }
    // Create three new orders

    public String generatedRandomCustomerName() {
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        int lengthCustomerName = 10;
        boolean useLettersCustomerName = true;
        boolean useSymbolsCustomerName = true;
        String generatedStringCustomerName = RandomStringUtils.random(lengthCustomerName, useLettersCustomerName, useSymbolsCustomerName);

        return generatedStringCustomerName;
    }

    public String generatedRandomCustomerPhone() {
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        int lengthCustomerPhone = 12;
        boolean useNumbersCustomerPhone = true;
        boolean useSymbolsCustomerPhone = false;
        String generatedStringCustomerPhone = RandomStringUtils.random(lengthCustomerPhone, useSymbolsCustomerPhone, useNumbersCustomerPhone);

        return generatedStringCustomerPhone;
    }

    public String generatedRandomComment() {
        int lengthComment = 15;
        boolean useLettersComment = true;
        boolean useNumbersComment = true;
        String generatedStringComment = RandomStringUtils.random(lengthComment, useLettersComment, useNumbersComment);

        return generatedStringComment;
    }

    // Created 3 orders with the same random data
    @Test
    public void generatedCreateOrder() {
        Order generatedOrder = new Order();
        generatedOrder.setStatus("OPEN");
        generatedOrder.setCourierId(0);
        generatedOrder.setCustomerName(generatedRandomCustomerName());
        generatedOrder.setCustomerPhone(generatedRandomCustomerPhone());
        generatedOrder.setComment(generatedRandomComment());
        generatedOrder.setId(0);

        Gson gsonGeneratedOrder = new Gson();

        Order[] generatedOrderArray = {generatedOrder, generatedOrder, generatedOrder};
        for (int i = 0; i < generatedOrderArray.length; i++)

            given()
                    .when()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(gsonGeneratedOrder.toJson(generatedOrder))
                    .log()
                    .all()
                    .post("/orders")
                    .then()
                    .log()
                    .all()
                    .statusCode(HttpStatus.SC_OK);

        Assertions.assertNotNull(generatedOrder.getId());

        Order[] checkNewCreatedOrders = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Order[].class);

        Assertions.assertEquals(0, generatedOrder.getCourierId());

    }

//    // Created 3 orders with the different random data
//    @Test
//    public void generatedCreateOrderDifferentRandomData() {
//        Order generatedFirstOrder = new Order();
//        generatedFirstOrder.setStatus("OPEN");
//        generatedFirstOrder.setCourierId(0);
//        generatedFirstOrder.setCustomerName(generatedRandomCustomerName());
//        generatedFirstOrder.setCustomerPhone(generatedRandomCustomerPhone());
//        generatedFirstOrder.setComment(generatedRandomComment());
//        generatedFirstOrder.setId(0);
//
//        Order generatedSecondOrder = new Order();
//        generatedSecondOrder.setStatus("OPEN");
//        generatedSecondOrder.setCourierId(0);
//        generatedSecondOrder.setCustomerName(generatedRandomCustomerName());
//        generatedSecondOrder.setCustomerPhone(generatedRandomCustomerPhone());
//        generatedSecondOrder.setComment(generatedRandomComment());
//        generatedSecondOrder.setId(0);
//
//        Order generatedThirdOrder = new Order();
//        generatedThirdOrder.setStatus("OPEN");
//        generatedThirdOrder.setCourierId(0);
//        generatedThirdOrder.setCustomerName(generatedRandomCustomerName());
//        generatedThirdOrder.setCustomerPhone(generatedRandomCustomerPhone());
//        generatedThirdOrder.setComment(generatedRandomComment());
//        generatedThirdOrder.setId(0);
//
//        Gson gsonGeneratedOrder = new Gson();
//
//        Order[] generatedOrderArray = {generatedFirstOrder, generatedSecondOrder, generatedThirdOrder};
//        for (int i = 0; i < generatedOrderArray.length; i++)
//
//            given()
//                    .when()
//                    .header("Content-Type", "application/json")
//                    .header("Authorization", "Bearer " + token)
//                    .body(gsonGeneratedOrder.toJson(generatedOrderArray))
////                    .body(gsonGeneratedOrder.toJson(generatedSecondOrder))
////                    .body(gsonGeneratedOrder.toJson(generatedThirdOrder))
//                    .log()
//                    .all()
//                    .post("/orders")
//                    .then()
//                    .log()
//                    .all()
//                    .statusCode(HttpStatus.SC_OK);
//
////        Assertions.assertNotNull(generatedOrder.getId());
//    }
//    Order[] checkNewCreatedOrdersDifferentData = given()
//            .when()
//            .header("Content-Type", "application/json")
//            .header("Authorization", "Bearer " + token)
//            .log()
//            .all()
//            .get("/orders")
//            .then()
//            .log()
//            .all()
//            .extract()
//            .as(Order[].class);
//
}
