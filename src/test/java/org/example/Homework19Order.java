package org.example;

import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.OrderPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;


// 3.2. Added checks for creating and searching created order
public class Homework19Order {

    static SetupFunctions setupFunctions;
    LoginPage loginPage;
    OrderPage orderPage;

    @BeforeAll
    public static void setUpProperty(){
        setupFunctions = new SetupFunctions();
    }

    @BeforeEach
    public void setUp(){
        open(setupFunctions.getBaseUrlWeb());
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
        closeWebDriver();
    }

    // 3.1. Creating an order
    @Test
    public void createOrder(){

        loginPage.locateUsernameAndInsertText(setupFunctions.getUsername());
        loginPage.locatePasswordAndInsertText(setupFunctions.getPassword());

        orderPage.clickButtonSignInAndGoToOrder();

        orderPage.usernameInsertText("New order");
        orderPage.phoneInsertNumbers("+37255566677");
        orderPage.commentInsertNumbers("Created");

        orderPage.buttonCreateOrder();

        orderPage.buttonOkWithCreatedOrderVisible();
    }

    // 3.2. Searching created order
    @Test
    public void searchOrderWithOrderNumber(){

        loginPage.locateUsernameAndInsertText(setupFunctions.getUsername());
        loginPage.locatePasswordAndInsertText(setupFunctions.getPassword());

        orderPage.clickButtonSignInAndGoToOrder();

        orderPage.usernameInsertText("New order");
        orderPage.phoneInsertNumbers("+37255566677");
        orderPage.commentInsertNumbers("Created");

        orderPage.buttonCreateOrder();

        orderPage.buttonOkWithCreatedOrderVisible();
        orderPage.clickButtonOkWithCreatedOrderVisible();

        orderPage.clickButtonStatusToSearchOrder();

        orderPage.fieldSearchOrder("4205");
        orderPage.clickButtonSearchOrder();
        orderPage.checkThatOrderCreated();
    }
}
