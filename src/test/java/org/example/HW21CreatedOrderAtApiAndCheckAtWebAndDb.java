package org.example;

import Homework21Integration.FieldsForCreateOrder;
import com.codeborne.selenide.Selenide;
import com.google.gson.Gson;
import db.DbManager;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.OrderPage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.codeborne.selenide.Selenide.open;
import static groovy.json.JsonOutput.toJson;
import static io.restassured.RestAssured.given;


public class HW21CreatedOrderAtApiAndCheckAtWebAndDb {

    static String token;
    static SetupFunctions setupFunctions;
    LoginPage loginPage;
    OrderPage orderPage;
    static Connection connection;


    @BeforeAll
    public static void setUpProperty(){
        setupFunctions = new SetupFunctions();
    }

    @BeforeEach
    public void setUp(){
        Selenide.open(setupFunctions.getBaseUrlWeb());
    }

    @BeforeEach
    public void setToken(){
//        open("http://51.250.6.164:3000/signin");
        token = setupFunctions.getToken();
        Selenide.localStorage().setItem("jwt", token);
        Selenide.refresh();
    }

    @BeforeEach
    public void setupLoginPage(){
        loginPage = new LoginPage();
    }

    @BeforeEach
    public void setupOrderPage(){
        orderPage = new OrderPage();
    }

    @AfterEach
    public void tearDown(){
        Selenide.closeWebDriver();
    }

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

    // To check at WEB created order at API
    @Test
    public void toCheckCreatedOrderFromApiToWeb(){

        String orderIdCreated = String.valueOf(toCreateOrder());

//        loginPage.locateUsernameAndInsertText(setupFunctions.getUsername());
//        loginPage.locatePasswordAndInsertText(setupFunctions.getPassword());

//        orderPage.clickButtonSignInAndGoToOrder();

        orderPage.clickButtonStatusToSearchOrder();

        orderPage.fieldSearchOrder(orderIdCreated);
        orderPage.clickButtonSearchOrder();
        orderPage.checkThatOrderCreated();
    }

    // To create a request to Database for searching created order at API
    public void toCreateRequestToDb(int orderId) {

        String sql = String.format("select * from orders where id = %d ;", orderId);
        System.out.println();

        try {
            System.out.println("Executing sql ... ");
            System.out.println("SQL is : " + sql);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery( sql );

            String statusFromDb = null;
            if (resultSet != null) {

                while ( resultSet.next() ) {
                    System.out.println(resultSet.getString(1) + resultSet.getString(2) + resultSet.getString(3));
                    statusFromDb = resultSet.getString(3);
                }

                Assertions.assertEquals( "OPEN", statusFromDb);

            } else {
                Assertions.fail("Result set is null");
            }

        } catch (SQLException e) {

            System.out.println("Error while executing sql ");
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            e.printStackTrace();

            Assertions.fail("SQLException");
        }
    }

    // Searching created order from API at Database
    @Test
    public void toCheckCreatedOrderFromApiToDb (){
        DbManager dbManager = new DbManager();
        connection = dbManager.connectionToDb();
        toCreateRequestToDb(toCreateOrder());
        dbManager.close(connection);
    }
}
