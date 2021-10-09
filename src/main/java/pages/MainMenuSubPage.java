package pages;

import io.qameta.allure.Step;
import model.Sections;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainMenuSubPage extends BasePage {
    private WebElement rootMenu;
    private Sections currentSection;

    public MainMenuSubPage(WebDriver wd, WebElement rootElement, Sections currentSection) {
        super(wd);
        rootMenu = rootElement;
        this.currentSection = currentSection;
    }

    @Step("Выбрать любую карту")
    public CardInfoPage selectAnyCard() {
        rootMenu.findElements(By.xpath(String.format(".//li/a[contains(@href,'%s')]", currentSection.getUrlPart())))
                .stream().limit(4)
                .findAny().get()
                .click();
        return new CardInfoPage(this.wd);
    }

    @Step("Выбрать любой вклад")
    public DepositInfoPage selectAnyDeposit() {
        rootMenu.findElements(By.xpath(String.format(".//li/a[contains(@href,'%s')]", currentSection.getUrlPart())))
                .stream().limit(4)
                .findAny().get()
                .click();
        return new DepositInfoPage(this.wd);
    }
}
