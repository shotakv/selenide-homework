package ge.tbcitacademy.util;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ge.tbcitacademy.data.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    //reusing old table old from previous task with few adjustments
    private static boolean containsText(SelenideElement element, String desiredText) {
        String elementText = element.getText();
        if (elementText.equals(desiredText)) {
            return true;
        }
        ElementsCollection childElements = element.$$(By.xpath(".//*"));
        for (SelenideElement childElement : childElements) {
            if (containsText(childElement, desiredText)) {
                return true;
            }
        }
        return false;
    }

    public static int findDesiredColumn(ElementsCollection headerRow, String desiredColumn) {
        int columnIndex = 0;
        for (SelenideElement headerElement : headerRow) {
            if (containsText(headerElement, desiredColumn)) {
                return columnIndex;
            }
            columnIndex++;
        }
        return -1;
    }

    public static Double discountCalculator(Double price,Double discount){
        return ((price * (100-discount))/100);
    }

    public static Double convertIntoDouble(String price){
        return Double.parseDouble(price.replace(",","").replace("$","").replace("-","").replace("%","").replace("US","").replace(" ",""));
    }

    public static void logMethodEnd(ITestResult result, String status) {
        LocalDateTime methodEndTime = LocalDateTime.now();
        System.out.println(Constants.METHOD + result.getMethod().getMethodName() + Constants.ENDED_AT + methodEndTime);
        long executionTime = result.getEndMillis() - result.getStartMillis();
        if (status.isEmpty()) {
            System.out.println(Constants.TEST_TOTAL_METHOD + result.getMethod().getMethodName() +Constants.SEMICOLON + executionTime + Constants.MS);
        } else {
            System.out.println(Constants.TEST_TOTAL_METHOD + result.getMethod().getMethodName() + Constants.SEMICOLON + executionTime + Constants.MS + " (" + status + ")");
        }
    }
    //informative comment
}
