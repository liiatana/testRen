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

@Getter
public class TestManager {

    public final Properties properties;
    public WebDriver wd;
    public File downloadsFolder;
    public String baseUrl;
    private String browser;
    private MainPage mainPage;

    public TestManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

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

    @Step("Открыть главную страницу")
    public MainPage getMainPage() {
        wd.get(baseUrl);
        return mainPage;
    }

    public void stop() {
        wd.close();
        wd.quit();
    }

}
