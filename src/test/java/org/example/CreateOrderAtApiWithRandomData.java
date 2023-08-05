package org.example;

import Homework21Integration.FieldsForCreateOrder;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.SetupFunctions;

import static io.restassured.RestAssured.given;

public class CreateOrderAtApiWithRandomData {

    static String token;
    static SetupFunctions setupFunctions;


    // Random data for field customer name, customer phone, comment
    public String randomCustomerName(){
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        int lengthFieldCustomerName = 10;
        boolean symbols = true;
        boolean numbers = false;
        String generatedStringCustomerName = RandomStringUtils.random(lengthFieldCustomerName, symbols, numbers);

        return generatedStringCustomerName;
    }

    public String randomCustomerPhone(){
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        int lengthFieldCustomerPhone = 12;
        boolean symbols = false;
        boolean numbers = true;
        String generatedStringCustomerPhone = RandomStringUtils.random(lengthFieldCustomerPhone, symbols, numbers);

        return generatedStringCustomerPhone;
    }

    public String randomCustomerComment(){
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        int lengthFieldCustomerComment = 15;
        boolean symbols = true;
        boolean numbers = true;
        String generatedStringCustomerComment = RandomStringUtils.random(lengthFieldCustomerComment,symbols, numbers);

        return generatedStringCustomerComment;
    }

    // To create an order with random data at API
    public int toCreateOrder() {
        FieldsForCreateOrder fieldsForCreateOrder = new FieldsForCreateOrder();
        fieldsForCreateOrder.setStatus("OPEN");
        fieldsForCreateOrder.setCourierId(0);
        fieldsForCreateOrder.setCustomerName(randomCustomerName());
        fieldsForCreateOrder.setCustomerPhone(randomCustomerPhone());
        fieldsForCreateOrder.setComment(randomCustomerComment());
        fieldsForCreateOrder.setId(0);

        Gson gson = new Gson();

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(fieldsForCreateOrder))
                .log()
                .all()
                .post(setupFunctions.getBaseUrl() + "/orders")
                .then()
                .log()
                .all()
                .extract()
                .response();

        FieldsForCreateOrder orderId = gson.fromJson(response.asString(), FieldsForCreateOrder.class);

        return orderId.getId();
    }

}
