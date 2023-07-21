package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryWebTest {

    @Test
    public void insertIncorrectLoginPasswordAndCheckError(){

        Configuration.browser = "edge";
    open("http://51.250.6.164:3000/signin");

        $(By.xpath("//input[@data-name='username-input']")).setValue("login12345");
        $(By.xpath("//input[@data-name='password-input']")).setValue ("password12345");
        $(By.xpath("//button[@data-name='signIn-button']")).click();
        $(By.xpath("//div[@data-name='authorizationError-popup']")).shouldBe(Condition.exist, Condition.visible);

    }

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

}
