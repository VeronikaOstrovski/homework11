package pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class CreatedOrderPage {

    public CreatedOrderPage clickButtonSearchOrder(){
        $(By.xpath("//button[@data-name='searchOrder-submitButton']")).click();
        return new CreatedOrderPage();
    }
}
