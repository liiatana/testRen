package base;

import customDrivers.CustomChromeDriver;
import helpers.AllureHelper;
import io.qameta.allure.Step;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.MainPage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Класс для управления тестовой средой
 */
@Getter
public class TestManager {

    /**
     * Данные из local.properties
     */
    public final Properties properties;
    /**
     * WebDriver
     */
    public WebDriver wd;
    /**
     * Место хранения скачиваемых файлов
     */
    public File downloadsFolder;
    /**
     * Главная/стартовая страница
     */
    public String baseUrl;
    private String browser;
    private MainPage mainPage;

    /**
     * Конструктор класса управления тестовой средой
     *
     * @param browser браузер
     */
    public TestManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    /**
     * Настроить среду
     *
     * @throws IOException
     */
    public void init() throws IOException {
        properties.load(new FileReader("src\\main\\resources\\local.properties"));

        switch (browser.toLowerCase()) {
            case "firefox":
                wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
            default:
                //wd = new ChromeDriver();
                wd = CustomChromeDriver.start();
        }

        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        baseUrl = properties.getProperty("web.baseUrl");

        mainPage = new MainPage(wd);
        AllureHelper.init().prepareEnvFile(this);

        downloadsFolder = new File(properties.getProperty("downloads.Folder"));
        if (downloadsFolder.exists()) FileUtils.cleanDirectory(downloadsFolder);
    }

    /**
     * Открыть главную страницу
     *
     * @return mainPage
     */
    @Step("Открыть главную страницу")
    public MainPage getMainPage() {
        wd.get(baseUrl);
        return mainPage;
    }

    /**
     * Закрыть драйвер
     */
    public void stop() {
        wd.close();
        wd.quit();
    }

}
