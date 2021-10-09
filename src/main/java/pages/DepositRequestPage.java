package pages;

import io.qameta.allure.Step;
import model.NewDepositInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;

/**
 * Страница для заполнения данных вклада
 */
public class DepositRequestPage extends BasePage {

    @FindBy(xpath = "//div[@class='calculator__content']/*//input[@type='checkbox']/parent::*")
    private WebElement inOffice;

    @FindBy(name = "amount")
    private WebElement amountTextElement;

    @FindBy(xpath = "//*[@data-property='period']/*[contains(@class,'range')]")
    private WebElement periodSlider;

    public DepositRequestPage(WebDriver wd) {
        super(wd);
        PageFactory.initElements(wd, this);
    }

    @Step("Заполнить условия вклада: {newDepositInfo}")
    public DepositRequestPage fillForm(NewDepositInfo newDepositInfo) {
        scrollTo(periodSlider);

        setCheckBox(inOffice, newDepositInfo.isInOffice());
        type(amountTextElement, String.valueOf(newDepositInfo.getAmount()));
        setSlider(periodSlider, newDepositInfo.getMonths(), newDepositInfo.getEnablePeriod());
        return this;
    }

    @Step("Скачать файл '{fileName}'")
    public File downloadGuide(String fileName, File toFolder) throws IOException {
        return downloadFile(wd.findElement(By.xpath(String.format("//div[@id='section_5']/*//a[contains(@href,'/%s')]", fileName))),
                fileName,
                toFolder);
    }
}
