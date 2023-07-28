package org.example;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.LoginPage;

import static com.codeborne.selenide.Selenide.*;

// Task 18
public class Homework18Login {

    static SetupFunctions setupFunctions;
    LoginPage loginPage;

    @BeforeAll
    public static void setupProperty(){
    setupFunctions = new SetupFunctions();
    }

    @BeforeEach
    public void setUp(){
        open(setupFunctions.getBaseUrlWeb());
    }

    @BeforeEach
    public void setupLoginPage (){
        loginPage = new LoginPage();
    }

    @AfterEach
    public void tearDown(){
        closeWebDriver();
    }

    // 1.1. Login with incorrect data and check an error
    @Test
    public void insertIncorrectLoginPasswordAndCheckError(){

//        LoginPage loginPage = new LoginPage();
        loginPage.locateUsernameAndInsertText("login12345");
        loginPage.locatePasswordAndInsertText("password12345");

        loginPage.clickSignIn();

        loginPage.authorizationError();

//        $(By.xpath("//input[@data-name='username-input']")).setValue("login12345");
//        $(By.xpath("//input[@data-name='password-input']")).setValue ("password12345");
//
//        $(By.xpath("//button[@data-name='signIn-button']")).click();
//
//        $(By.xpath("//div[@data-name='authorizationError-popup']")).shouldBe(Condition.exist, Condition.visible);
    }

    // 1.2. Login with correct data and check an order page
    @Test
    public void insertCorrectLoginPasswordAndCheckError(){
//
//        SetupFunctions setupFunctions = new SetupFunctions();
////        Configuration.browser = "edge";
//        open("http://51.250.6.164:3000/signin");

        loginPage.locateUsernameAndInsertText(setupFunctions.getUsername());
        loginPage.locatePasswordAndInsertText(setupFunctions.getPassword());

        loginPage.clickSignIn();

//        $(By.xpath("//input[@data-name='username-input']")).setValue(setupFunctions.getUsername());
//        $(By.xpath("//input[@data-name='password-input']")).setValue (setupFunctions.getPassword());
//
//        $(By.xpath("//button[@data-name='signIn-button']")).click();

        $(By.xpath("//button[@data-name='createOrder-button']")).shouldBe(Condition.exist, Condition.visible);
        $(By.xpath("//button[@data-name='openStatusPopup-button']")).shouldBe(Condition.exist, Condition.visible);
    }

    // Login with incorrect data and check an error and repeat successful login
    @Test
    public void insertIncorrectLoginPasswordAndCheckErrorAndRepeatSuccessfulLogin(){

//        SetupFunctions setupFunctions = new SetupFunctions();
//        System.setProperty("selenide.browser", "safari");

//        open("http://51.250.6.164:3000/signin");

        loginPage.locateUsernameAndInsertText("test-login");
        loginPage.locatePasswordAndInsertText("test-password");
        loginPage.clickSignIn();

//        $(By.xpath("//input[@data-name='username-input']")).setValue("test-login");
//        $(By.xpath("//input[@data-name='password-input']")).setValue("test-password");
//
//        $(By.xpath("//button[@data-name='signIn-button']")).click();

        $(By.xpath("//button[@data-name='authorizationError-popup-close-button']")).shouldBe(Condition.exist, Condition.visible);

        $(By.xpath("//button[@data-name='authorizationError-popup-close-button']")).click();

        loginPage.locateUsernameAndInsertText(setupFunctions.getUsername());
        loginPage.locatePasswordAndInsertText(setupFunctions.getPassword());

        loginPage.clickSignIn();

//        $(By.xpath("//input[@data-name='username-input']")).setValue(setupFunctions.getUsername());
//        $(By.xpath("//input[@data-name='password-input']")).setValue(setupFunctions.getPassword());
//
//        $(By.xpath("//button[@data-name='signIn-button']")).click();

        $(By.xpath("//input[@data-name='phone-input']")).shouldBe(Condition.exist, Condition.visible);

    }

    // 1.4.1. Field Login 1 symbol ("l") and field Password 8 symbols ("password") and check Error that button "Sign in" an inactive
    @Test
    public void buttonSignInInactiveLogin1SymbolPassword8Symbols(){

//        System.setProperty("selenide.browser", "safari");
//        open("http://51.250.6.164:3000/signin");

        loginPage.locateUsernameAndInsertText("l");
        loginPage.locatePasswordAndInsertText("password");

        loginPage.buttonSignInDisable();

        loginPage.errorForLoginField();

//        $(By.xpath("//input[@data-name='username-input']")).setValue("l");
//        $(By.xpath("//input[@data-name='password-input']")).setValue("password");

//        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);

//        $(By.xpath("//*[@data-name='username-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist, Condition.visible);
    }

    // 1.4.2. Field Login 2 symbols ("LO") and field Password 1 symbol ("P") and check Error that button "Sign in" an inactive
    @Test
    public void buttonSignInInactiveLogin2SymbolsPassword1Symbol(){

//        System.setProperty("selenide.browser","safari");
//        open("http://51.250.6.164:3000/signin");

        loginPage.locateUsernameAndInsertText("LO");
        loginPage.locatePasswordAndInsertText("P");

        loginPage.buttonSignInDisable();

        loginPage.errorForPasswordField();

//        $(By.xpath("//input[@data-name='username-input']")).setValue("LO");
//        $(By.xpath("//input[@data-name='password-input']")).setValue("P");

//        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);

//        $(By.xpath("//*[@data-name='password-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist,Condition.visible);

    }

    // 1.4.3. Field Login empty(space) and field Password empty(space) and check Error that button "Sign in" an inactive
    @Test
    public void buttonSignInInactiveLoginSpacePasswordSpace() {

//        System.setProperty("selenide.browser","safari");
//        open("http://51.250.6.164:3000/signin");

        loginPage.locateUsernameAndInsertText(" ");
        loginPage.locatePasswordAndInsertText(" ");

        loginPage.buttonSignInDisable();

//        $(By.xpath("//input[@data-name='username-input']")).setValue(" ");
//        $(By.xpath("//input[@data-name='password-input']")).setValue(" ");

//        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);

        loginPage.errorForLoginField();
        loginPage.errorForPasswordField();

//        $(By.xpath("//*[@data-name='username-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist, Condition.visible);
//        $(By.xpath("//*[@data-name='password-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist,Condition.visible);

        //Assertions.assertEquals(Condition.disabled, Condition.);
    }
    }

