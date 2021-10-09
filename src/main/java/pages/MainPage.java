package pages;

import io.qameta.allure.Step;
import model.Sections;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Главная страница
 */
public class MainPage extends BasePage {

    String primaryNav = "//nav[@data-nav='primary']/ul/li[@data-nav-parent]";

    public MainPage(WebDriver wd) {
        super(wd);
    }

    /**
     * Переход с главной страницы в заданный раздел
     *
     * @param sectionName имя подраздела( Карты, Вклады..)
     * @return страница с подменю выбранного раздела
     */
    @Step("Перейти в раздел '{sectionName.name}'")
    public MainMenuSubPage openSection(Sections sectionName) {
        WebElement webElement = wd.findElements(By.xpath(primaryNav))
                .stream()
                .filter(x -> x.getText().equals(sectionName.getName()))
                .findAny().orElse(null);
        if (webElement != null) webElement.click();
        return new MainMenuSubPage(this.wd, webElement, sectionName);
    }

}
