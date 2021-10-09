package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CardInfoPage extends BasePage {

    @FindBy(xpath = "//footer[@class='advertising__footer']/*//*[contains(@class,'button')]")
    private WebElement acquireButton;

    public CardInfoPage(WebDriver wd) {
        super(wd);
        PageFactory.initElements(wd, this);
    }

    @Step("Нажать 'Перейти к оформлению'")
    public CardRequestPage cardAcure() {
        acquireButton.click();
        return new CardRequestPage(this.wd);
    }
}
