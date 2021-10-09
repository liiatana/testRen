package base;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

import static java.util.Arrays.asList;

/**
 * Базовый класс для всех тестов
 */
public class TestBase {
    protected static final TestManager tmanager = new TestManager(System.getProperty("browser", BrowserType.CHROME));
    Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite
    public void setUp() throws Exception {
        tmanager.init();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        logger.info("Start test: {} with parameters {}", m.getName(), asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m, ITestResult testResult) {
        logger.info("Test {} finished with status = {}", m.getName(), testResult.getStatus());
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        tmanager.stop();
    }
}
