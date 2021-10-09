package base;

import customDrivers.CustomChromeDriver;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.MainPage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.nio.file.Files.delete;
import static java.nio.file.Paths.get;

@Getter
public class TestManager {

    private final Properties properties;
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
        prepareAllureFile();

        downloadsFolder = new File(properties.getProperty("downloads.Folder"));
        if (downloadsFolder.exists()) FileUtils.cleanDirectory(downloadsFolder);
    }

    public MainPage getMainPage() {
        wd.get(baseUrl);
        return mainPage;
    }

    public void stop() {
        wd.close();
        wd.quit();
    }

    //TODO в идеале вынести
    private void prepareAllureFile() throws IOException {

        File file = new File(properties.getProperty("allure.PropertyFile"));
        if (file.exists()) FileUtils.cleanDirectory(new File(file.getParent()));

        if (file.exists()) {
            delete(get(properties.getProperty("allure.PropertyFile")));
        }

        addEnvironmentInfo(file, "BaseUrl", properties.getProperty("web.baseUrl"));
        addEnvironmentInfo(file, "Browser", ((RemoteWebDriver) wd).getCapabilities().getBrowserName().toLowerCase());
        addEnvironmentInfo(file, "BrowserVersion", ((RemoteWebDriver) wd).getCapabilities().getVersion());


    }

    private void addEnvironmentInfo(File file, String parameter, String value) {
        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(System.lineSeparator());
            writer.write(parameter + "=" + value);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
