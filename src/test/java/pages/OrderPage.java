package pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;


// 3.1. Created page object for OrderPage
public class OrderPage {

    public OrderPage clickButtonSignInAndGoToOrder(){
        $(By.xpath("//button[@data-name='signIn-button']")).click();
        return new OrderPage();
    }

    public void usernameInsertText(String text){
        $(By.xpath("//input[@data-name='username-input']")).setValue(text);
    }

    public void phoneInsertNumbers(String tel){
        $(By.xpath("//input[@data-name='phone-input']")).setValue(tel);
    }

    public void commentInsertNumbers(String comment){
        $(By.xpath("//input[@data-name='comment-input']")).setValue(comment);
    }

    public void buttonCreateOrder(){
        $(By.xpath("//button[@data-name='createOrder-button']")).click();
    }

    public void buttonOkWithCreatedOrderVisible(){
        $(By.xpath("//button[@data-name='orderSuccessfullyCreated-popup-ok-button']")).shouldBe(Condition.exist, Condition.visible);
    }

    public void clickButtonOkWithCreatedOrderVisible(){
        $(By.xpath("//button[@data-name='orderSuccessfullyCreated-popup-ok-button']")).click();
    }

    public void clickButtonStatusToSearchOrder(){
        $(By.xpath("//button[@data-name='openStatusPopup-button']")).click();
    }

    public void fieldSearchOrder (String value){
        $(By.xpath("//input[@data-name='searchOrder-input']")).setValue(value);
    }

    public void clickButtonSearchOrder(){
        $(By.xpath("//button[@data-name='searchOrder-submitButton']")).click();
    }

    public void checkThatOrderCreated(){
        $(By.xpath("//input[@data-name='useless-input']")).shouldBe(Condition.exist, Condition.visible);
    }
}
