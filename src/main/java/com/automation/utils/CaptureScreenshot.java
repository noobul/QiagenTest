package com.automation.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureScreenshot {

    private static final DateFormat dateFormat = new SimpleDateFormat("yy_MM_dd_mm_ss");

    public static String captureScreen(WebDriver driver, String screenName) throws IOException {
        TakesScreenshot screen = (TakesScreenshot) driver;
        File src = screen.getScreenshotAs(OutputType.FILE);
        String dest = "./Test-Screenshots/" + screenName + ".png";

        File target = new File(dest);
        FileUtils.copyFile(src, target);

        return dest;
    }

    public static String generateFileName(String method){
        Date date = new Date();
        String fileName = method + "_" + dateFormat.format(date);
        return fileName;
    }

    public static String generateFileName(ITestResult result){
        Date date = new Date();
        String fileName = result.getMethod().getMethodName() + "_" + dateFormat.format(date);
        return fileName;
    }
}
