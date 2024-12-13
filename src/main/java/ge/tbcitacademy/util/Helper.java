package ge.tbcitacademy.util;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import ge.tbcitacademy.data.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Helper {
    private static WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(10)); //only way to solve the problem


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
        return Double.parseDouble(price.replace(",","").replace("$","").replace("-","").replace("%","").replace("US","").replace(" ","").replace("₾",""));
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

    public static List<Double> collectAllOffers(){
        List<Double> listOfOffers = new ArrayList<>();
        int pageCount = 1;
        try{
            WebElement lastPageNumber = $x("//div[contains(@class,'flex justify-center items-center w-9 h-9')][last()]");
            wait.until(ExpectedConditions.visibilityOf(lastPageNumber));
            pageCount = Integer.parseInt(lastPageNumber.getText());

            for (int i = 1; i <= pageCount; i++) {
                ElementsCollection tempList = $$x("//a[contains(@class,'group flex flex-col')]//h4[@weight='bold']");
                tempList.forEach(webElement -> {
                    String tempString = webElement.getText();
                    listOfOffers.add(Double.parseDouble(tempString.substring(0, tempString.length() - 1)));
                });

                SelenideElement nextPageElement = $x("//img[@alt='right arrow']");

                jsClick(nextPageElement);

                $x("//img[@alt='right arrow']").shouldBe(clickable);
                $x("//a[contains(@class,'group flex flex-col')]").shouldBe(clickable); // waiting for the first offer to be clickable
            }
        }
        catch (Exception e){
            ElementsCollection tempList = $$x("//a[contains(@class,'group flex flex-col')]//h4[@weight='bold']");
            tempList.forEach(webElement -> {
                String tempString = webElement.getText();
                listOfOffers.add(Double.parseDouble(tempString.substring(0, tempString.length() - 1)));
            });
        }

        return listOfOffers;
    }

    public static Double sortAndQueryFirstOfferPrice(String sortingMethod){
        SelenideElement sortingElement = $x("//button[contains(@class,'flex items-center gap-2')]").shouldBe(clickable);
        sortingElement.click();
        SelenideElement sortPriceDescendingElement = $x("//p[@class='cursor-pointer' and contains(.,'"+ sortingMethod + "')]").shouldBe(clickable);
        sortPriceDescendingElement.click();
        $x("//img[@alt='right arrow']").shouldBe(clickable);
        ElementsCollection sortedListOfOffers = $$x("//a[contains(@class,'group flex flex-col')]//h4[@weight='bold']");
        String firstSortedOfferPriceElement = sortedListOfOffers.get(0).getText();
        return Double.parseDouble(firstSortedOfferPriceElement.substring(0, firstSortedOfferPriceElement.length() - 1));
    }

    public static void jsClick(SelenideElement element){
        executeJavaScript("arguments[0].click();", element);
    }

    public static String fillOutFormAndBringStudentName(String firstName,String lastName,String gender, String mobileNumber){
        SelenideElement firstNameInput = $("#firstName");
        SelenideElement lastNameInput = $("#lastName");
        SelenideElement genderRadioButton = $x(String.format("//input[@type='radio' and @value='%s']",gender)).shouldBe(clickable);
        SelenideElement mobileNumberInput = $("#userNumber");

        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        Helper.jsClick(genderRadioButton);
        mobileNumberInput.sendKeys(mobileNumber);

        SelenideElement submitButton = $("#submit").scrollTo().shouldBe(clickable);
        Helper.jsClick(submitButton);

        $x("//div[@class='modal-content']").shouldBe(visible); // waiting to for popup to appear
        ElementsCollection headerRow = $$x("//thead//th");
        int desiredColumnIndex = Helper.findDesiredColumn(headerRow,Constants.VALUES);

        ElementsCollection studentNameRow = $$x("//td[text()='Student Name']/parent::tr/td");

        return studentNameRow.get(desiredColumnIndex).getText();
    }

    //informative comment
}
