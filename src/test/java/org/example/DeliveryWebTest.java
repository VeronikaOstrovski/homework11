package org.example;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryWebTest {

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
