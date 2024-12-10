import ge.tbcitacademy.util.BaseTest;
import ge.tbcitacademy.util.Helper;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import ge.tbcitacademy.data.Constants;
import com.codeborne.selenide.*;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class SelenideTests2 extends BaseTest {
    @Test
    public void validateDemosDesign() {
        open(Constants.TELERIK_URL);
        getWebDriver().manage().window().maximize();
        ElementsCollection webCardsList = $$x(Constants.WEB_SECTION_ELEMENT_XPATH+ "/parent::div//img");
        SelenideElement webSectionHeading = $x("//h2[text()='Web']");// just so we can scroll where the whole Web sections is visible
        webSectionHeading.scrollTo();
        webCardsList.forEach(selenideElement -> {
            selenideElement.hover();
            SelenideElement hoverOverlay = selenideElement.$x("./following-sibling::div[@class='LinkContainer']");
            hoverOverlay.shouldBe(appear, visible);
            selenideElement.$x(".//parent::div").shouldHave(Condition.cssValue("background-color", Constants.PURPLE_RGBA_COLOR_TEXT));
        });

        SelenideElement kendoBoxElement = $x(Constants.WEB_SECTION_ELEMENT_XPATH+"//img");
        kendoBoxElement.hover();
        SelenideElement kendoBoxOverlayContentElement = $x(Constants.WEB_SECTION_ELEMENT_XPATH+"//div[@class='LinkContainer']");
        kendoBoxOverlayContentElement.shouldHave(Condition.text(Constants.UI_VUE_DEMO_TEXT));


        SelenideElement desktopSectionButton = $x("//a[@href='#desktop']");
        desktopSectionButton.click();
        ElementsCollection desktopSectionElements = $$x("//div[@id='ContentPlaceholder1_C337_Col00']/parent::div/div/a").filterBy(Condition.attributeMatching("href", Constants.MICROSOFT_SUPPORT_REGEX_TEXT));
        desktopSectionElements.forEach(System.out::println);

        SelenideElement mobileSectionButton = $x("//a[@href='#mobile']");
        mobileSectionButton.click();
        ElementsCollection telerikXamarinUIBoxElement = $(byId("ContentPlaceholder1_C340_Col01")).$$x("./a//img");

        String[] supportTitlesStrings = {Constants.APP_STORE_TEXT, Constants.GOOGLE_PLAY_TEXT, Constants.MICROSOFT_TEXT};

        for (int i = 0; i > telerikXamarinUIBoxElement.size(); i++) {
            telerikXamarinUIBoxElement.get(i).shouldHave(Condition.attribute("title", supportTitlesStrings[i]));
        }

        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        ElementsCollection stickyNavBarContentElement = $$x("//div[@data-tlrk-plugin='navspy']/a");
        stickyNavBarContentElement.shouldHave(texts(Constants.WEB_TEXT, Constants.DESKTOP_TEXT, Constants.MOBILE_TEXT, Constants.REPORTING_TEXT, Constants.TESTING_AND_MOCKING_TEXT, Constants.DEBUGGING_TEXT, Constants.CONVERSATIONAL_UI_TEXT, Constants.SITEFINITY_CMS));


        ElementsCollection titleElements = $$x("//h2[@class='u-mb0 h3']");

        for (int i = 0; i > stickyNavBarContentElement.size(); i++) {
            stickyNavBarContentElement.get(i).click();
            titleElements.get(i).shouldBe(appear, visible);
            //$x("//h2[text()='"+ stickyNavBarContentElement.get(i).getText() + "']").shouldBe(appear,visible); this was first solution but for some reason SiteFinityCMS takes you to Web Content Management
            stickyNavBarContentElement.get(i).shouldHave(Condition.attribute("class", "NavAlt-anchor u-b0 is-active"));
        }


    }

    @Test
    public void validateOrderMechanics() {
        open(Constants.TELERIK_URL);
        getWebDriver().manage().window().maximize();
        SelenideElement pricingElement = $x("//li[@class='TK-Menu-Item']//a[@href='/purchase.aspx']");
        pricingElement.click();
        SelenideElement devCraftCompletePriceElement = $x(Constants.DEVCRAFT_COMPLETE_IDENTIFIER_XPATH + "//h3//span[@class='u-dib u-fs60 u-fw4']");
        devCraftCompletePriceElement.scrollTo();
        String priceFromOptionsMenu = devCraftCompletePriceElement.getText();
        SelenideElement devCraftCompleteBuyButton = $x(Constants.DEVCRAFT_COMPLETE_IDENTIFIER_XPATH+ "//a");
        devCraftCompleteBuyButton.click();
        SelenideElement loginPromptDismiss = $x("//i[@class='far fa-times label u-cp']");
        loginPromptDismiss.click();
        SelenideElement priceFromPurchasingMenu = $x("//h4[@class='u-fr e2e-total-price']");
        //Unit price is correct
        priceFromPurchasingMenu.shouldHave(Condition.text(priceFromOptionsMenu));

        //Increase the term, validate that the price is added correctly according to its percentage.
        SelenideElement termDropdownButton = $x("//td[@data-label='Maintenance & Support']//button");
        termDropdownButton.click();

        SelenideElement oneYearOption = $x("//span[text()='+1 year']//ancestor::li");
        double oneYearPlusDiscount = Helper.convertIntoDouble($x(Constants.ONE_YEAR_PLUS_ELEMENT_XPATH).shouldBe(visible).getText());
        oneYearOption.shouldBe(visible).click();
        double basePrice = Double.parseDouble(priceFromOptionsMenu.replace(",", ""));
        double maintenanceAndSupportBasePrice = Helper.convertIntoDouble($x("//div[@class='col-md-4']//span[@class='u-fr e2e-maintenance-price']").getText());


        Double maintenancePriceAfter1YearDiscount = Helper.discountCalculator(maintenanceAndSupportBasePrice, oneYearPlusDiscount);

        double expectedPriceAfterOneYearDiscount = basePrice + maintenancePriceAfter1YearDiscount;

        double calculatedPriceAfterOneYearDiscount = Helper.convertIntoDouble($x("//h4[@class='u-fr e2e-total-price']").getText());

        Assert.assertEquals(calculatedPriceAfterOneYearDiscount, expectedPriceAfterOneYearDiscount);

        //Increase the Quantity and validate that the saving is displayed correctly according to the discount.
        SelenideElement quantityDropdownElement = $x(Constants.LICENSES_ELEMENT_XPATH + "//button");
        quantityDropdownElement.click();

        SelenideElement desiredQuantityAmountOption = $x("//div[normalize-space(text()) = '" + Constants.DESIRED_QUANTITY_AMOUNT + "']/parent::li");

        SelenideElement discountAmountForSelected = $x("//span[contains(text(),'" + Constants.DESIRED_QUANTITY_AMOUNT + "')]//following-sibling::span[@class='u-w100p label page-body--success u-tar']");

        double discountForDesiredOption = Helper.convertIntoDouble(discountAmountForSelected.shouldBe(visible).getText().replace("Discount", ""));

        desiredQuantityAmountOption.click();

        double savedMoneyAfterDiscount = (basePrice * discountForDesiredOption) / 100;

        SelenideElement quantitySavingsElement = $x(Constants.LICENSES_ELEMENT_XPATH + "//div[contains(@class,'savings')]");
        double quantitySavingsDouble = Helper.convertIntoDouble(quantitySavingsElement.shouldBe(visible).getText().replace("Save", ""));

        Assert.assertEquals(quantitySavingsDouble, savedMoneyAfterDiscount);

        //Validate SubTotal value.
        double quantityPriceAfterIncreasing = Helper.discountCalculator(basePrice, discountForDesiredOption) * 2;

        termDropdownButton.click();
        //trying to get term values again since when quantity is higher, term offers even bigger discount
        Double DifferentQuantityOneYearPlusDiscount = Helper.convertIntoDouble($x(Constants.ONE_YEAR_PLUS_ELEMENT_XPATH).shouldBe(visible).getText());

        Double newMaintenancePriceAfter1YearDiscount = Helper.discountCalculator(maintenanceAndSupportBasePrice, DifferentQuantityOneYearPlusDiscount) * 2;


        double subTotalAmount = quantityPriceAfterIncreasing + newMaintenancePriceAfter1YearDiscount;

        double calculatedSubTotalAmount = Helper.convertIntoDouble($x("//div[text()='Subtotal']//following-sibling::div").getText());

        Assert.assertEquals(calculatedSubTotalAmount, subTotalAmount);

        //Validate Total Discounts - hover over the question mark and validate that each discount is displayed correctly
        SelenideElement toolTipElement = $x("//i[@class='far fa-question-circle tooltip-icon']");
        toolTipElement.hover();

        double licenseToolTipDiscountAmount = Helper.convertIntoDouble($x(Constants.TOOLTIP_IDENTIFIER_XPATH+"//span[text()='Licenses']/parent::div//div/span").shouldBe(visible).getText());
        double maintenanceToolTipDiscountAmount = Helper.convertIntoDouble($x(Constants.TOOLTIP_IDENTIFIER_XPATH+"//span[text()='Maintenance & Support']/parent::div//div/span").shouldBe(visible).getText());

        double totalSavedForMaintenance = ((maintenanceAndSupportBasePrice * 2) * DifferentQuantityOneYearPlusDiscount) / 100;
        double totalSavedForLicenses = savedMoneyAfterDiscount * 2;

        Assert.assertEquals(licenseToolTipDiscountAmount, totalSavedForLicenses);
        Assert.assertEquals(maintenanceToolTipDiscountAmount, totalSavedForMaintenance);

        //Validate Total value
        double finalCalculatedPriceAfterDiscounts = Helper.convertIntoDouble($x("//h4[@class='u-fr e2e-total-price']").getText());

        Assert.assertEquals(finalCalculatedPriceAfterDiscounts, subTotalAmount);

        //another check for Total
        double websiteCalculatedLicenses = Helper.convertIntoDouble($x(Constants.TOTAL_DISCOUNTS_PANEL_XPATH+"//span[text()='Licenses']/following-sibling::span").shouldBe(visible).getText());
        double websiteCalculatedMaintenance = Helper.convertIntoDouble($x(Constants.TOTAL_DISCOUNTS_PANEL_XPATH+"//span[text()='Maintenance & Support']/following-sibling::span").shouldBe(visible).getText());
        double websiteCalculatedTotalDiscounts = Helper.convertIntoDouble($x(Constants.TOTAL_DISCOUNTS_PANEL_XPATH+"//div[text()='Total Discounts ']/parent::div/following-sibling::span").shouldBe(visible).getText());

        double alternativeFinalPrice = websiteCalculatedLicenses + websiteCalculatedMaintenance - websiteCalculatedTotalDiscounts;
        Assert.assertEquals(finalCalculatedPriceAfterDiscounts, alternativeFinalPrice);


        //after validations
        SelenideElement continueAsGuestButton = $x("//span[text()='Continue as Guest']//parent::button");
        continueAsGuestButton.click();

        //register details
        SelenideElement firstNameInput = $(byId("biFirstName"));
        SelenideElement lastNameInput = $(byId("biLastName"));
        SelenideElement emailInput = $(byId("biEmail"));
        SelenideElement companyInput = $(byId("biCompany"));
        SelenideElement phoneInput = $(byId("biPhone"));
        SelenideElement addressInput = $(byId("biAddress"));
        SelenideElement countryDropdownInput = $x("//kendo-combobox[@id='biCountry']//input");
        SelenideElement cityInput = $(byId("biCity"));
        SelenideElement zipCodeInput = $(byId("biZipCode"));

        //fake data here
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String company = faker.company().name();
        String phone = Constants.DEFAULT_NUMBER_INITIAL_TEXT + faker.number().digits(6);
        String address = faker.address().streetAddress();
        String country = faker.address().country();
        String city = faker.address().city();
        String zipCode = faker.address().zipCode();

        //inputting data
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        emailInput.sendKeys(email);
        companyInput.sendKeys(company);
        phoneInput.sendKeys(phone);
        addressInput.sendKeys(address);
        countryDropdownInput.sendKeys(country);
        actions().sendKeys(Keys.TAB).perform();
        cityInput.sendKeys(city);
        zipCodeInput.sendKeys(zipCode);

        SelenideElement backButton = $x("//span[text()='Back']//parent::a");
        backButton.click();
        continueAsGuestButton.click();

        $x("//div[@class='page-loading-container']").shouldNotBe(visible); // waiting for loader to go back to billing information

        ElementsCollection inputElements = $$x("//div[@class='col-md-10']//input");
        inputElements.forEach(element -> {
            element.shouldBe(empty);
        });

    }


    @Test
    public void chainedLocatorsTest() {
        open(Constants.DEMO_QA_BOOKS_URL);
        getWebDriver().manage().window().maximize();
        ElementsCollection oReillyBooks = $(".rt-tbody").$$(".rt-tr-group").filter(Condition.text(Constants.O_REILLY_TEXT)).filter(Condition.text(Constants.JAVASCRIPT_TEXT));
        oReillyBooks.forEach(book -> {
            Assert.assertFalse(book.find("img").getAttribute("src").isEmpty());
        });
    }

    @Test
    public void softAssertTest() {
        open(Constants.DEMO_QA_BOOKS_URL);
        getWebDriver().manage().window().maximize();
        ElementsCollection oReillyBooks = $(".rt-tbody").findAll(".rt-tr-group").filter(Condition.text(Constants.O_REILLY_TEXT)).filter(Condition.text(Constants.JAVASCRIPT_TEXT));
        sfa.assertEquals(oReillyBooks.size(), 10);
        ElementsCollection booksList = $(".rt-tbody").findAll(".rt-tr-group");
        String firstBookName = booksList.first().find("a").getText();
        sfa.assertEquals(firstBookName, Constants.GIT_POCKET_GUIDE_TEXT);

        sfa.assertAll();
    }

    //This comment is informative
}
