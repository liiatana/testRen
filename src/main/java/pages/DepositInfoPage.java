package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DepositInfoPage extends BasePage {
    @FindBy(xpath = "//footer[@class='advertising__footer']/*//li[@class='list__item']")
    private WebElement officeButton;

    @FindBy(xpath = "//a[contains(@href,'/contributions/')]")
    private WebElement topMenu;

    public DepositInfoPage(WebDriver wd) {
        super(wd);
        PageFactory.initElements(wd, this);
    }

    @Step("Выбрать 'В отделении'")
    public DepositRequestPage depositInOffice() {
        officeButton.click();
        topMenu.click();
        return new DepositRequestPage(this.wd);
    }
}
