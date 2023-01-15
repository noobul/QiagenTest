package com.automation;

import com.automation.config.Configuration;
import com.automation.config.Configuration.LogType;
import com.automation.config.Configuration.TimeOuts;
import com.automation.utils.CaptureScreenshot;
import com.automation.utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.openqa.selenium.NoSuchElementException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;

import static com.automation.config.Configuration.CHROME_BROWSER;

public class TestBase extends ExtentManager {

    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest logger;
    public static Actions actions;

    /**
     * It's used to start the browser with the desired settings
     */
    @BeforeTest(alwaysRun = true)
    public void setup(){
        startBrowser(CHROME_BROWSER);
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Configuration.BROWSER_ARGUMENTS);
        options.addArguments("--disable.gpu");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        setFullHD();
        goToUrl(Configuration.TEST_URL);
        waitPageLoad(TimeOuts.FIVE_SEC_WAIT);
        extent = ExtentManager.getInstance("master-branch");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method){
        System.out.println("TEST STARTED: " + method.getName());
        logger = extent.createTest(method.getName());
    }

    /**
     * Logs the results at the end of each test
     * Restarts browser at the end of each test to avoid issues
     */

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("FAILED: " + result.getName());
            System.out.println(result.getThrowable().toString());
            logReport(LogType.FAIL,result.getThrowable().toString());
            if (driver != null) {
                logger.addScreenCaptureFromPath(CaptureScreenshot.captureScreen(driver, CaptureScreenshot.generateFileName(result)));
                driver.manage().deleteAllCookies();
                restartBrowser(CHROME_BROWSER);
            }
        }

        else if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("SKIPPED: ");
            logReport(LogType.SKIP ,result.getThrowable().toString());
            restartBrowser(CHROME_BROWSER);
        }

        else {
            System.out.println("PASSED: " + result.getName());
            logReport(LogType.PASS, "Pass");
            restartBrowser(CHROME_BROWSER);
        }

        extent.flush();
    }

    @AfterTest(alwaysRun = true)
    public void quitDriver(){
        if(driver != null)
            driver.quit();
    }

    private void startBrowser(String browser){
        if(browser.equalsIgnoreCase("Firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        } else if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
    }

    private void restartBrowser(String browser){
        driver.quit();
        startBrowser(browser);
        setFullHD();
        goToUrl(Configuration.TEST_URL);
        waitPageLoad(Configuration.TimeOuts.FIVE_SEC_WAIT);
    }


    public void setFullHD(){
        Dimension dimension = new Dimension(1920, 1080);
        driver.manage().window().setSize(dimension);
    }

    public void goToUrl(String url) {
        driver.get(url);
    }

    public void logReport(String logType, String logDetails) {
        switch (logType) {
            case LogType.PASS -> logger.log(Status.PASS, MarkupHelper.createLabel(logDetails, ExtentColor.TEAL));
            case LogType.FAIL -> logger.log(Status.FAIL, MarkupHelper.createLabel(logDetails, ExtentColor.RED));
            case LogType.WARNING ->
                    logger.log(Status.WARNING, MarkupHelper.createLabel(logDetails, ExtentColor.ORANGE));
            case LogType.INFO -> logger.log(Status.INFO, MarkupHelper.createLabel(logDetails, ExtentColor.BLUE));
        }
    }

    // can be used for verbose logging for verified conditions
    public void logInfo(String msg){
        logReport(LogType.INFO, msg);
    }

    public boolean checkTrueCondition(boolean condition, String passMsg, String failMsg) throws SecurityException, IOException {
        if (condition)
            logReport(LogType.PASS, passMsg);
        else {
            logReport(LogType.FAIL, failMsg + takeScreenShot());
        }
        return condition;
    }

    public ExtentTest takeScreenShot() throws SecurityException, IOException {
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        return logger.addScreenCaptureFromPath(CaptureScreenshot
                .captureScreen(driver, CaptureScreenshot
                        .generateFileName(stack[stack.length -25].getMethodName())));
    }

    public void clearSendKeys(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public void waitAndClick(WebElement element) {
        waitForElementToBeVisibleFluentFast(element);
        waitForElementToBeClickable(element);
        try {
            element.click();
        }catch (Exception e){
            logInfo(""+element.toString()+" is already clicked.");
        }

    }

    // Waits
    public void waitPageLoad(int timePageLoad) {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timePageLoad));
    }

    public void waitPage(int time) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        return (new WebDriverWait (driver, Duration.ofSeconds(TimeOuts.THREE_MIN_WAIT)).
                until(ExpectedConditions.elementToBeClickable(element)));
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        return (new WebDriverWait (driver, Duration.ofSeconds(TimeOuts.THREE_MIN_WAIT)).
                until(ExpectedConditions.visibilityOf(element)));
    }

    // Wait 60 seconds for an element to be present on a page, checking for its presence every 5 seconds
    public WebElement waitForElementToBeVisibleFluentFast(WebElement element) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TimeOuts.SIXTY_SEC_WAIT))
                .pollingEvery(Duration.ofSeconds(TimeOuts.FIVE_SEC_WAIT))
                .ignoring(NoSuchElementException.class);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public boolean checkIfElementIsVisible(WebElement element){
        try {
            return element.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public WebElement findByXpath(String element){
        return driver.findElement(By.xpath(""+element+""));
    }

    public String getDayLater(int days){
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        fmt.format("%te", cal);
        return fmt.toString();
    }

    public String getMonth(int days){
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        fmt.format("%tB", cal);
        return fmt.toString();
    }

}

