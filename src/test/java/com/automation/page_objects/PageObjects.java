package com.automation.page_objects;

import com.automation.TestBase;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.security.Key;

public class PageObjects extends TestBase {
    WebDriver driver;

    public PageObjects(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // web elements
    @FindBy (xpath = "//div[@data-bdd = 'farefinder-option-bundles']")
    private WebElement bundlesOption;

    @FindBy (xpath = "//button[@data-bdd = 'farefinder-package-bundleoption-car']")
    private WebElement bundleCarOption;

    @FindBy (xpath = "//input[@data-bdd = 'farefinder-package-origin-location-input']")
    private WebElement fromInput;

    @FindBy(xpath = "//input[@data-bdd = 'farefinder-package-destination-location-input']")
    private WebElement toInout;

    @FindBy(xpath = "//div[@data-bdd = 'farefinder-package-startdate-input']")
    private WebElement startDate;

    @FindBy(xpath = "//div[@data-bdd = 'farefinder-package-enddate-input']")
    private WebElement endDate;

    @FindBy(xpath = "//select['farefinder-package-pickuptime-input']")
    private WebElement pickUpTimeDropDown;

    @FindBy(xpath = "//select[@data-bdd = 'farefinder-package-pickuptime-input']/option[@value = 'Evening']")
    private WebElement eveningOption;

    @FindBy(xpath = "//button[@data-bdd = 'farefinder-package-search-button']")
    private WebElement findDealButton;

    @FindBy(xpath = "//div[@class = 'uitk-spacing uitk-spacing-margin-blockstart-three']")
    private WebElement resultDiv;

    @FindBy(xpath = "//div[@class = 'farefinder-container']")
    private WebElement container;

    // test methods
    public void clickOnBundlesOptions(){
        waitAndClick(bundlesOption);
    }

    public void clickOnBundleCarOption(){
        waitAndClick(bundleCarOption);
    }

    public void inputFromOption(String input){
        clearSendKeys(fromInput, input);
    }

    public void inputToDestination(String input){
        clearSendKeys(toInout, input);

    }

    public void selectEveningDeparture(){
        waitAndClick(pickUpTimeDropDown);
        waitAndClick(eveningOption);
    }

    public void clickOnFindADeal(){
        waitAndClick(findDealButton);
    }

    public void selectDay(int days){
        String currentMonth = getMonth(days);
        String nextDay = getDayLater(days);
        waitAndClick(findByXpath("//td[contains(@aria-label, '"+currentMonth+" "+nextDay+"')]"));
    }

    public void selectNextDay(){
        waitPage(5);
        waitAndClick(startDate);
        selectDay(1);
    }

    public void selectTwentyDaysLater(){
        waitPage(5);
        selectDay(21);
    }

    public boolean checkThatAResultIsDisplayed() throws IOException {
        waitForElementToBeVisibleFluentFast(resultDiv);
        boolean check = checkIfElementIsVisible(resultDiv);
        return checkTrueCondition(check, "At least a result is displayed.",
                "Not results are displayed");
    }
}
