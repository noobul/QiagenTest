package com.automation.test_classes;

import com.automation.page_objects.PageObjects;
import com.automation.TestBase;
import com.automation.utils.DProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class QiagenTest extends TestBase {

    PageObjects pageObjects;
    SoftAssert softAssert;

    @BeforeMethod(alwaysRun = true)
    public void initializePages(){
        pageObjects = new PageObjects(driver);
        softAssert = new SoftAssert();
    }

    @Test(dataProvider = "firstTest", dataProviderClass = DProvider.class)
    public void firstTest(String from, String to) throws IOException {
        pageObjects.clickOnBundlesOptions();
        pageObjects.clickOnBundleCarOption();
        pageObjects.inputFromOption(from);
        pageObjects.inputToDestination(to);
        pageObjects.selectNextDay();
        pageObjects.selectTwentyDaysLater();
        pageObjects.selectEveningDeparture();
        pageObjects.clickOnFindADeal();
        softAssert.assertTrue(pageObjects.checkThatAResultIsDisplayed());
        softAssert.assertAll();
    }
}
