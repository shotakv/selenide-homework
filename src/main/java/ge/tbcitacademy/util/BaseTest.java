package ge.tbcitacademy.util;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BaseTest {
    public JavascriptExecutor js;
    public Faker faker;
    public SoftAssert sfa;



    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) throws Exception {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            WebDriverRunner.setWebDriver(new ChromeDriver(options));
            Configuration.browserCapabilities = options;
            Configuration.browser = "chrome";
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--width=1920 height=1080");
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            WebDriverRunner.setWebDriver(new FirefoxDriver(options));
            Configuration.browserCapabilities = options;
            Configuration.browser = "firefox";
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            WebDriverRunner.setWebDriver(new EdgeDriver(options));
            Configuration.browserCapabilities = options;
            Configuration.browser = "edge";
        }
        else {
            throw new Exception(Constants.BROWSER_NOT_SUPPORTED_TEXT);
        }
        Configuration.timeout = 10000;
        sfa = new SoftAssert();
        faker = new Faker();
    }

    @AfterMethod
    public void tearDown() {
        getWebDriver().quit();
    }

}
