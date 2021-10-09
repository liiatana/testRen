package customDrivers;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

import static java.io.File.separator;
import static org.openqa.selenium.chrome.ChromeOptions.CAPABILITY;

@Getter
public class CustomChromeDriver {

    ChromeOptions opts;
    Map<String, Object> prefs;
    DesiredCapabilities capabilities;

    CustomChromeDriver() {
        opts = new ChromeOptions();
        prefs = new HashMap<>();
        capabilities = new DesiredCapabilities();

        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", separator + "downloadFiles");
        prefs.put("download.directory_upgrade", true);
        opts.setExperimentalOption("prefs", prefs);

        //opts.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--incognito");
        opts.addArguments("window-size=1366,768");
        opts.addArguments("--disable-infobars");
        opts.addArguments("--disable-notifications");
        capabilities.setCapability(CAPABILITY, opts);
        opts.merge(capabilities);
    }

    public static WebDriver start() {
        CustomChromeDriver customChromeDriver = new CustomChromeDriver();
        return new ChromeDriver(customChromeDriver.getOpts());
    }
}
