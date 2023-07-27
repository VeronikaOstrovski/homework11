package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;

// Task 18
public class DeliveryWebTest {

    // 1.1. Login with incorrect data and check an error
    @Test
    public void insertIncorrectLoginPasswordAndCheckError(){

//        Configuration.browser = "edge";
    open("http://51.250.6.164:3000/signin");

        $(By.xpath("//input[@data-name='username-input']")).setValue("login12345");
        $(By.xpath("//input[@data-name='password-input']")).setValue ("password12345");

        $(By.xpath("//button[@data-name='signIn-button']")).click();

        $(By.xpath("//div[@data-name='authorizationError-popup']")).shouldBe(Condition.exist, Condition.visible);
    }

    // 1.2. Login with correct data and check an order page
    @Test
    public void insertCorrectLoginPasswordAndCheckError(){

        SetupFunctions setupFunctions = new SetupFunctions();
//        Configuration.browser = "edge";
        open("http://51.250.6.164:3000/signin");

        $(By.xpath("//input[@data-name='username-input']")).setValue(setupFunctions.getUsername());
        $(By.xpath("//input[@data-name='password-input']")).setValue (setupFunctions.getPassword());

        $(By.xpath("//button[@data-name='signIn-button']")).click();

        $(By.xpath("//button[@data-name='createOrder-button']")).shouldBe(Condition.exist, Condition.visible);
        $(By.xpath("//button[@data-name='openStatusPopup-button']")).shouldBe(Condition.exist, Condition.visible);
    }

    // Login with incorrect data and check an error and repeat successful login
    @Test
    public void insertIncorrectLoginPasswordAndCheckErrorAndRepeatSuccessfulLogin(){

        SetupFunctions setupFunctions = new SetupFunctions();
        System.setProperty("selenide.browser", "safari");

        open("http://51.250.6.164:3000/signin");
        $(By.xpath("//input[@data-name='username-input']")).setValue("test-login");
        $(By.xpath("//input[@data-name='password-input']")).setValue("test-password");

        $(By.xpath("//button[@data-name='signIn-button']")).click();

        $(By.xpath("//button[@data-name='authorizationError-popup-close-button']")).shouldBe(Condition.exist, Condition.visible);

        $(By.xpath("//button[@data-name='authorizationError-popup-close-button']")).click();

        $(By.xpath("//input[@data-name='username-input']")).setValue(setupFunctions.getUsername());
        $(By.xpath("//input[@data-name='password-input']")).setValue(setupFunctions.getPassword());

        $(By.xpath("//button[@data-name='signIn-button']")).click();

        $(By.xpath("//input[@data-name='phone-input']")).shouldBe(Condition.exist, Condition.visible);

    }

    // 1.4.1. Field Login 1 symbol ("l") and field Password 8 symbols ("password") and check Error that button "Sign in" an inactive
    @Test
    public void buttonSignInInactiveLogin1SymbolPassword8Symbols(){

        System.setProperty("selenide.browser", "safari");
        open("http://51.250.6.164:3000/signin");

        $(By.xpath("//input[@data-name='username-input']")).setValue("l");
        $(By.xpath("//input[@data-name='password-input']")).setValue("password");

        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);
        $(By.xpath("//*[@data-name='username-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist, Condition.visible);

    }

    // 1.4.2. Field Login 2 symbols ("LO") and field Password 1 symbol ("P") and check Error that button "Sign in" an inactive
    @Test
    public void buttonSignInInactiveLogin2SymbolsPassword1Symbol(){

        System.setProperty("selenide.browser","safari");
        open("http://51.250.6.164:3000/signin");

        $(By.xpath("//input[@data-name='username-input']")).setValue("LO");
        $(By.xpath("//input[@data-name='password-input']")).setValue("P");

        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);
        $(By.xpath("//*[@data-name='password-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist,Condition.visible);

    }

    // 1.4.3. Field Login empty(space) and field Password empty(space) and check Error that button "Sign in" an inactive
    @Test
    public void buttonSignInInactiveLoginSpacePasswordSpace() {

        System.setProperty("selenide.browser","safari");
        open("http://51.250.6.164:3000/signin");

        $(By.xpath("//input[@data-name='username-input']")).setValue(" ");
        $(By.xpath("//input[@data-name='password-input']")).setValue(" ");

        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);
        $(By.xpath("//*[@data-name='username-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist, Condition.visible);
        $(By.xpath("//*[@data-name='password-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist,Condition.visible);



        //Assertions.assertEquals(Condition.disabled, Condition.);
    }

    // Task 2.2.
    // 1. Successful login Mobiil-ID
    @Test
    public void buttonLogiSisseActiveMobiilId(){

        Configuration.browser="safari";
        open("https://profile.esto.ee/login/mobile-id");

        $(By.xpath("//*[@id='phone-field']/ ..//input[@class='esto-text-input-container__input']")).setValue("+37258066606");
        $(By.xpath("//*[@id='pin-field']/..//input[@class='esto-text-input-container__input']")).setValue("48812050267");
//        $(By.xpath("//*[@id='pin-field']/ ..//input")).setValue("48812050267");

        $(By.xpath("//button[@estocypressdata='mobile-id-login-button']")).shouldBe(Condition.exist, Condition.visible);
    }

    // 2. Successful login Smart-ID
    @Test
    public void buttonLogiSisseActiveSmartId(){

        System.setProperty("selenide.browser","safari");
        open ("https://profile.esto.ee/login/smart-id");

        $(By.xpath("//*[@id='login-field']/..//input[@class='esto-text-input-container__input']")).setValue("48812050267");

        $(By.xpath("//button[@class='form-button esto-button esto-button__accent']")).shouldBe(Condition.exist,Condition.visible);
    }

    // 3. Successful login Parol
    @Test
    public void buttonLogiSisseActiveParool(){

        System.setProperty("selenide.browser","safari");
        open("https://profile.esto.ee/login/password");

        $(By.xpath("//*[@id='login-field']/..//input[@class='esto-text-input-container__input']")).setValue("Login-test");
        $(By.xpath("//*[@id='password-field']/..//input[@class='esto-text-input-container__input']")).setValue("Parool-test");

        $(By.xpath("//button[@class='form-button esto-button esto-button__accent']")).shouldBe(Condition.exist, Condition.visible);
    }

    // 4. Unsuccessful login Smart-ID
    @Test
    public void errorForUnsuccessfulLoginSmartId (){

        System.setProperty("selenide.browser", "safari");
        open("https://profile.esto.ee/login/smart-id");

        $(By.xpath("//*[@id='login-field']/..//input[@class='esto-text-input-container__input']")).setValue("488120502677");

        $(By.xpath("//button[@class='form-button esto-button esto-button__accent']")).click();

        $(By.xpath("//*[@class='error-text ng-star-inserted']")).shouldBe(Condition.exist, Condition.visible);
    }

    // 5. Unsuccessful login Parool
    @Test
    public void errorForUnsuccessfulLoginParool (){

        Configuration.browser="safari";
        open("https://profile.esto.ee/login/password");

        $(By.xpath("//*[@id='login-field']/..//input[@class='esto-text-input-container__input']")).setValue("");
        $(By.xpath("//*[@id='password-field']/..//input[@class='esto-text-input-container__input']")).setValue(" ");

        $(By.xpath("//button[@class='form-button esto-button esto-button__accent']")).click();

        $(By.xpath("//span[@class='ng-star-inserted']")).shouldBe(Condition.exist,Condition.visible);
    }

    // Lesson 19

    @BeforeEach
    public void setUp() {
        open("http://51.250.6.164:3000/signin");
    }
        @Test
        public void insertBeforeAllIncorrectLoginPasswordAndCheckError(){

            $(By.xpath("//input[@data-name='username-input']")).setValue("login12345");
            $(By.xpath("//input[@data-name='password-input']")).setValue ("password12345");

            $(By.xpath("//button[@data-name='signIn-button']")).click();

            $(By.xpath("//div[@data-name='authorizationError-popup']")).shouldBe(Condition.exist, Condition.visible);
            closeWebDriver();

        }

    }

