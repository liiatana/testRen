package pages;

import io.qameta.allure.Step;
import model.NewClientInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CardRequestPage extends BasePage {

    @FindBy(name = "ClientLastName")
    private WebElement lastName;

    @FindBy(name = "ClientName")
    private WebElement firstName;

    @FindBy(name = "ClientSecondName")
    private WebElement secondName;

    @FindBy(name = "ClientMobilePhone")
    private WebElement mobilePhone;

    @FindBy(name = "AdditionalEmail")
    private WebElement email;

    @FindBy(xpath = "//*[@data-ignoreid='1']")
    private WebElement withoutSecondName;

    @FindBy(id = "s2-styler")
    private WebElement chooseRegion;

    public CardRequestPage(WebDriver wd) {
        super(wd);
        PageFactory.initElements(wd, this);
    }

    @Step("Заполнить данные заявителя: {newClientInfo}")
    public void fillForm(NewClientInfo newClientInfo) {
        scrollTo(email);

        type(lastName, newClientInfo.getLastName());
        type(firstName, newClientInfo.getFirstName());

        if (newClientInfo.isWithoutSecondName()) setCheckBox(withoutSecondName, newClientInfo.isWithoutSecondName());
        else type(secondName, newClientInfo.getSecondName());

        type(mobilePhone, newClientInfo.getMobile());
        type(email, newClientInfo.getEmail());

        setDropdownList(chooseRegion, newClientInfo.getRegion());
    }
}
