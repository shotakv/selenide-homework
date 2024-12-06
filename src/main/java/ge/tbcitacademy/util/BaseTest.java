package ge.tbcitacademy.util;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BaseTest {
    public JavascriptExecutor js;
    public Faker faker;


    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("firefox") String browser) throws Exception {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            WebDriverRunner.setWebDriver(new ChromeDriver());
            Configuration.browser = "chrome";
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            WebDriverRunner.setWebDriver(new FirefoxDriver());
            Configuration.browser = "firefox";
        } else {
            throw new Exception(Constants.BROWSER_NOT_SUPPORTED_TEXT);
        }
        Configuration.timeout = 10000;
        faker = new Faker();
    }

    @AfterMethod
    public void tearDown() {
        getWebDriver().quit();
    }

}
