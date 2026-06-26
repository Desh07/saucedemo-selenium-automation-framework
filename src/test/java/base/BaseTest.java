package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.PageLoadStrategy;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import utils.ExtentManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class BaseTest {

    private static final String BASE_URL = "https://www.saucedemo.com/";

    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        resolveChromeDriverPath().ifPresent(
                path -> System.setProperty("webdriver.chrome.driver", path.toString())
        );

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        driver.get(BASE_URL);

        extent = ExtentManager.getExtentReports();
    }

    private Optional<Path> resolveChromeDriverPath() {
        Path cacheRoot = Paths.get(
                System.getProperty("user.home"),
                ".cache",
                "selenium",
                "chromedriver",
                "win64"
        );

        if (!Files.isDirectory(cacheRoot)) {
            return Optional.empty();
        }

        try (Stream<Path> versions = Files.list(cacheRoot)) {
            return versions
                    .filter(Files::isDirectory)
                    .sorted(Comparator.reverseOrder())
                    .map(versionDir -> versionDir.resolve("chromedriver.exe"))
                    .filter(Files::isRegularFile)
                    .findFirst();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {
            if (test != null) {
                if (result.getStatus() == ITestResult.SUCCESS) {
                    test.pass("PASSED: " + result.getName());
                } else if (result.getStatus() == ITestResult.FAILURE) {
                    test.fail(result.getThrowable());
                }
            }
        } catch (Exception ignored) {}

        if (driver != null) {
            driver.quit();
        }

    }
    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}
