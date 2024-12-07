package Homework1;

import ge.tbcitacademy.data.Constants;
import ge.tbcitacademy.util.BaseTest;
import ge.tbcitacademy.util.Helper;
import org.testng.annotations.Test;
import com.codeborne.selenide.*;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class SelenideTests extends BaseTest {
    @Test
    public void validateBundleOffers() {
        open(Constants.TELERIK_URL);
        getWebDriver().manage().window().maximize();
        SelenideElement pricingElement = $x("//li[@class='TK-Menu-Item']//a[@href='/purchase.aspx']");
        pricingElement.click();
        ElementsCollection devCraftIUContentElements = $$x("//th[contains(@class,'UI')]"); // checking all thead elements
        devCraftIUContentElements.forEach(element -> {
            element.shouldNotHave(Condition.text(Constants.MOCKING_SOLUTION_TEXT));
            element.shouldNotHave(Condition.text(Constants.ISSUE_ESCALATION_TEXT));
            element.shouldNotHave(Condition.text(Constants.END_TO_END_REPORT_TEXT));
        });
        ElementsCollection devCraftCompleteContentElements = $$x("//th[contains(@class,'Complete')]"); //checking all thead elements
        devCraftCompleteContentElements.forEach(element -> {
            element.shouldNotHave(Condition.text(Constants.ISSUE_ESCALATION_TEXT));
            element.shouldNotHave(Condition.text(Constants.END_TO_END_REPORT_TEXT));
        });
        SelenideElement devCraftUltimateSupportElement = $x("//th[contains(@class,'Ultimate')]//p[@class='u-fs12 u-fwn u-mb0']");
        devCraftUltimateSupportElement.shouldHave(Condition.text(Constants.ISSUE_ESCALATION_TEXT));
        SelenideElement devCraftUltimateContentElement = $x("//th[contains(@class,'Ultimate')]//div[@class='u-m-m0 u-m-mt1 u-pl1 u-pr0']");
        devCraftUltimateContentElement.shouldHave(Condition.text(Constants.END_TO_END_REPORT_TEXT));


        ElementsCollection headersRow = $$x("//tr[@class='Pricings-name']//th[@class]");
        int devCraftCompleteColumnIndex = Helper.findDesiredColumn(headersRow, "DevCraft Complete");
        int devCraftUltimateColumnIndex = Helper.findDesiredColumn(headersRow, "DevCraft Ultimate");

        SelenideElement detailedComparisonButtonElement = $x("//button[contains(text(),'detailed comparison')]");
        detailedComparisonButtonElement.shouldBe(clickable).click();

        ElementsCollection telerikTestStudioRow = $$x("//p[contains(text(),'Telerik Test Studio Dev Edition')]//parent::td//following-sibling::td");
        int index = 0;
        while (index < telerikTestStudioRow.size()) {
            if (index == devCraftUltimateColumnIndex) {
                telerikTestStudioRow.get(index).shouldHave(Condition.text(Constants.SUPPORTED_DOT_TEXT));
            } else {
                telerikTestStudioRow.get(index).shouldNotHave(Condition.text(Constants.SUPPORTED_DOT_TEXT));
            }
            index++;
        }

        ElementsCollection kendoUISupportRow = $$x("//td[contains(text(),'Kendo UI for jQuery')]//parent::td//following-sibling::td");
        kendoUISupportRow.forEach(selenideElement -> {
            selenideElement.shouldHave(Condition.text(Constants.SUPPORTED_DOT_TEXT));
        });

        ElementsCollection reportServerRow = $$x("//p[contains(text(),'Telerik Report Server')]//parent::td//following-sibling::td");
        reportServerRow.get(devCraftUltimateColumnIndex).shouldHave(Condition.text(Constants.ONE_INSTANCE_15_USERS_TEXT));

        ElementsCollection telerikReportingRow = $$x("//p[contains(text(),'Telerik Reporting')]//parent::td//following-sibling::td");
        for (int i = 0; i < telerikReportingRow.size(); i++) {
            if (i == devCraftCompleteColumnIndex || i == devCraftUltimateColumnIndex) {
                telerikReportingRow.get(i).shouldHave(Condition.text(Constants.SUPPORTED_DOT_TEXT));
            } else {
                telerikTestStudioRow.get(i).shouldNotHave(Condition.text(Constants.SUPPORTED_DOT_TEXT));
            }
        }

        ElementsCollection accessOnDemandRow = $$x("//td[contains(text(),'Access to on-demand videos')]//parent::td//following-sibling::td");
        accessOnDemandRow.forEach(selenideElement -> {
            selenideElement.shouldHave(Condition.text(Constants.SUPPORTED_DOT_TEXT));
        });

        telerikReportingRow.last().scrollTo(); // scrolling to the middle of the table to check if the sticky offer names still stay visible

        ElementsCollection stickyHeadrowsElement = $$x("//div[@class='PricingTable-Sticky']/div");
        stickyHeadrowsElement.forEach(selenideElement -> {
            selenideElement.shouldBe(visible);
        });

        stickyHeadrowsElement.shouldHave(texts(Constants.DEVCRAFT_UI_TEXT, Constants.DEVCRAFT_COMPLETE_TEXT, Constants.DEVCRAFT_ULTIMATE_TEXT));
    }


    @Test
    public void validateIndividualOffers() {
        open(Constants.TELERIK_URL);
        getWebDriver().manage().window().maximize();
        SelenideElement pricingElement = $x("//li[@class='TK-Menu-Item']//a[@href='/purchase.aspx']");
        pricingElement.click();

        SelenideElement individualProductsElement = $x("//a[@href='#individual-products']");
        individualProductsElement.click();

        SelenideElement kendoUIBoxHoveringElement = $x("//h3[text()='Kendo UI']//parent::div[@class='Box-header u-pb3 u-pt3 u-pen']");
        SelenideElement kendoUINinja = $x("//img[@title='Kendo Ui Ninja']/parent::div");
        kendoUIBoxHoveringElement.hover();
        kendoUINinja.shouldBe(appear, visible);

        SelenideElement kendoReactBoxHoveringElement = $x("//h3[text()='KendoReact']//parent::div[@class='Box-header u-pb3 u-pt3 u-pen']");
        SelenideElement kendoReactNinja = $x("//img[@title='Kendo React Ninja']/parent::div");
        kendoReactBoxHoveringElement.hover();
        kendoReactNinja.shouldBe(appear, visible);


        SelenideElement kendoUIBoxElement = $x("//div[@data-opti-expid='Kendo UI']");
        SelenideElement kendoUIDropdownElement = kendoUIBoxElement.$x(".//a[contains(@class,'Btn Dropdown-control u-b1 u-c-black')]");
        actions().scrollByAmount(200, 0).perform();
        kendoUIDropdownElement.shouldHave(Condition.text(Constants.PRIORITY_SUPPORT_TEXT));

        SelenideElement kendoReactBoxElement = $x("//div[@id='ContentPlaceholder1_C714_Col01']//div[@data-opti-expid='KendoReact']");
        SelenideElement kendoReactDropdownElement = kendoReactBoxElement.$x(".//a[contains(@class,'Btn Dropdown-control u-b1 u-c-black')]");
        kendoReactDropdownElement.shouldHave(Condition.text(Constants.PRIORITY_SUPPORT_TEXT));

        SelenideElement kendoReactPriceElement = kendoReactBoxElement.$x(".//h2//span[@class='js-price']");
        kendoReactPriceElement.shouldHave(Condition.text(Constants.KENDO_REACT_PRIORITY_SUPPORT_PRICE));

        //alternate way of checking the price

        kendoUIBoxHoveringElement.scrollTo(); // scrolling till the box is in the view fully
        kendoUIDropdownElement.click();
        SelenideElement prioritySupportOptionKendoUIElement = kendoUIBoxElement.$x(".//a[@value='Priority Support']");
        SelenideElement prioritySupportOptionKendoUIPriceElement = prioritySupportOptionKendoUIElement.$x(".//div[@class='u-fr u-fs14 u-c-black']");
        prioritySupportOptionKendoUIPriceElement.shouldHave(Condition.text(Constants.KENDO_UI_PRIORITY_SUPPORT_PRICE));


    }


    @Test
    public void checkBoxTest() {
        open(Constants.HEROKU_CHECKBOXES_URL);
        getWebDriver().manage().window().maximize();

        SelenideElement firstCheckBox = $x("//input[following-sibling::text()=' checkbox 1']");
        firstCheckBox.setSelected(true);
        firstCheckBox.shouldBe(checked);
        SelenideElement secondCheckBox = $x("//input[normalize-space(following-sibling::text())='checkbox 2']");

        firstCheckBox.shouldHave(attribute("type", "checkbox"));
        secondCheckBox.shouldHave(attribute("type", "checkbox"));

    }

    @Test
    public void dropDownTest() {
        open(Constants.HEROKU_DROPDOWN_URL);
        getWebDriver().manage().window().maximize();
        SelenideElement dropDownElement = $("#dropdown");
        dropDownElement.getSelectedOption().shouldHave(text(Constants.PLEASE_SELECT_OPTION_TEXT));
        dropDownElement.selectOption(Constants.OPTION_2_TEXT);
        dropDownElement.getSelectedOption().shouldHave(text(Constants.OPTION_2_TEXT));
    }

    @Test
    public void collectionsTest() {
        open(Constants.DEMO_QA_TEXTBOX_URL);
        getWebDriver().manage().window().maximize();
        SelenideElement fullNameElement = $("#userName");
        SelenideElement emailElement = $("#userEmail");
        SelenideElement currentAddressElement = $("#currentAddress");
        SelenideElement permanentAddressElement = $("#permanentAddress");
        SelenideElement submitButtonElement = $("#submit");

        //saving fake values into variables so they can be reused
        String fullName = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String currentAddress = faker.address().streetAddress();
        String permanentAddress = faker.address().streetAddress();

        fullNameElement.sendKeys(fullName);
        emailElement.sendKeys(email);
        currentAddressElement.sendKeys(currentAddress);
        permanentAddressElement.sendKeys(permanentAddress);
        submitButtonElement.scrollTo();
        submitButtonElement.click();

        ElementsCollection submitResultElements = $$x("//div[@id='output']//p");

        submitResultElements.shouldHave(texts(fullName, email, currentAddress, permanentAddress));

    }

    //This comment is precise and understandable
}
