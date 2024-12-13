import com.codeborne.selenide.SelenideElement;
import ge.tbcitacademy.data.Constants;
import ge.tbcitacademy.data.DataSupplier;
import ge.tbcitacademy.util.BaseTest;
import ge.tbcitacademy.util.Helper;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedSwoopTests extends BaseTest {
    @Test(dataProvider = "offersData",dataProviderClass = DataSupplier.class)
    public void checkSaleValuesTest(String offerProviderName){
        open(Constants.SWOOP_MAIN_PAGE_URL);
        SelenideElement sportCategoryButtonElement = $x("//a[@href='/category/110/sporti/' and descendant::img]");
        sportCategoryButtonElement.click();

        SelenideElement offerPricesBoxElement = $x(String.format("//div[text()='%s']/parent::div/following-sibling::div[descendant::h4]",offerProviderName)).shouldBe(visible).scrollTo();
        double discountedPrice = Helper.convertIntoDouble(offerPricesBoxElement.find(By.xpath(".//h4[contains(@class,'text-primary_black-100-value')]")).getText());
        double fullPrice = Helper.convertIntoDouble(offerPricesBoxElement.find(By.xpath(".//h4[contains(@class,'text-md leading-5 font-tbcx-regular')]")).getText());
        double discountAmount = Helper.convertIntoDouble(offerPricesBoxElement.find(By.xpath(".//p")).getText());

        System.out.println(discountedPrice);

        double calculatedDiscountedPrice = fullPrice - Math.round((fullPrice * discountAmount) /100);

        Assert.assertEquals(discountedPrice,calculatedDiscountedPrice);
    }

    @Test(dataProvider = "offersData",dataProviderClass = DataSupplier.class)
    public void validateCartBehaviour(String offerProviderName){
        open(Constants.SWOOP_MAIN_PAGE_URL);
        SelenideElement sportCategoryButtonElement = $x("//a[@href='/category/110/sporti/' and descendant::img]");
        sportCategoryButtonElement.click();

        SelenideElement offerElement = $x(String.format("//div[text()='%s']//ancestor::a",offerProviderName));
        offerElement.should(appear).scrollTo().shouldBe(clickable).click();

        SelenideElement addToCartButton = $x("//p[text()='კალათაში დამატება']/parent::button");
        addToCartButton.should(appear).shouldBe(clickable).click();

        SelenideElement cartButtonElement = $x("//img[@src='/icons/basket-black.svg']/parent::a");
        cartButtonElement.click();

        SelenideElement offerNameInTheCart = $x("//div[@class='flex w-full tablet:gap-6 gap-4']//div[@class='flex flex-col h-full']/p[@weight='regular']").shouldBe(visible);

        Assert.assertEquals(offerNameInTheCart.getText(),offerProviderName);
    }

}
