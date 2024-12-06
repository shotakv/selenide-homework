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
}
