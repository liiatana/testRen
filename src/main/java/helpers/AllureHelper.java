package helpers;

import base.TestManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class AllureHelper {

    public static AllureHelper allureHelper;

    private AllureHelper() {
    }

    public static AllureHelper init() {
        if (allureHelper == null) {
            allureHelper = new AllureHelper();
        }
        return allureHelper;
    }

    public void prepareEnvFile(TestManager testManager) throws IOException {
        File file = new File(testManager.getProperties().getProperty("allure.PropertyFile"));
        if (file.exists())
            FileUtils.cleanDirectory(new File(file.getParent()));

        addEnvironmentInfo(file, "BaseUrl", testManager.getProperties().getProperty("web.baseUrl"));
        addEnvironmentInfo(file, "Browser", ((RemoteWebDriver) testManager.getWd()).getCapabilities().getBrowserName().toLowerCase());
        addEnvironmentInfo(file, "BrowserVersion", ((RemoteWebDriver) testManager.getWd()).getCapabilities().getVersion());
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
