import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ge.tbcitacademy.data.Constants;
import ge.tbcitacademy.util.BaseTest;
import ge.tbcitacademy.util.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedSwoopTests2 extends BaseTest {
    private String categoryName;

    public ParametrizedSwoopTests2(String categoryName){
        this.categoryName = categoryName;
    }

    @Test
    public void filterTest(){
        open(Constants.SWOOP_MAIN_PAGE_URL);
        SelenideElement categoryImgElement = $x(String.format("//img[@alt='%s']", categoryName));
        categoryImgElement.click();
        List<Double> allOfferPrices = Helper.collectAllOffers();
        Collections.sort(allOfferPrices);
        Double firstSortedPrice = Helper.sortAndQueryFirstOfferPrice(Constants.PRICE_ASCENDING_TEXT);
        Assert.assertEquals(firstSortedPrice,allOfferPrices.get(0));
    }

    @Test
    public void rangeTest(){
        open(Constants.SWOOP_MAIN_PAGE_URL);

        SelenideElement categoryImgElement = $x(String.format("//img[@alt='%s']", categoryName));
        categoryImgElement.click();

        SelenideElement priceRangeElement = $x("//div[@role='radio' and contains(.,'"+ Constants.PRICE_RANGE_TEXT + "')]").shouldBe(clickable);
        executeJavaScript("arguments[0].click();", priceRangeElement);

        SelenideElement rangeLowLimitElement = $x("//p[text()='"+ Constants.PRICE_RANGE_FROM_TEXT + "']//following-sibling::input").shouldBe(clickable);
        SelenideElement rangeHighLimitElement = $x("//p[text()='" + Constants.PRICE_RANGE_TO_TEXT + "']//following-sibling::input");

        String lowLimitString = rangeLowLimitElement.getAttribute("value");

        String highLimitString = rangeHighLimitElement.getAttribute("value");

        int lowLimit = Integer.parseInt(lowLimitString);
        int highLimit = Integer.parseInt(highLimitString);


        List<Double> listOfOffers = Helper.collectAllOffers();

        Assert.assertTrue(listOfOffers.stream().allMatch(price -> price >= lowLimit && price <= highLimit));
    }

}
