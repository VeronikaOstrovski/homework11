package pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public void locateUsernameAndInsertText(String text){
        $(By.xpath("//input[@data-name='username-input']")).setValue(text);
    }

    public void locatePasswordAndInsertText(String text){
        $(By.xpath("//input[@data-name='password-input']")).setValue (text);
    }

    public void clickSignIn(){
        $(By.xpath("//button[@data-name='signIn-button']")).click();
    }

    public void buttonSignInDisable(){
        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);
    }

    public void authorizationError(){
        $(By.xpath("//div[@data-name='authorizationError-popup']")).shouldBe(Condition.exist, Condition.visible);
    }

    public void errorForLoginField() {
        $(By.xpath("//*[@data-name='username-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist, Condition.visible);
    }

    public void errorForPasswordField() {
        $(By.xpath("//*[@data-name='password-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist, Condition.visible);
    }

}
