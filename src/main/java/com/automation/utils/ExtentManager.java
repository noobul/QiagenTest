package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    public static ExtentReports extent;

    public static ExtentReports getInstance(String version){
        if (extent == null)
            createInstance("./test_report.html", version);
        return extent;
    }

    private synchronized static ExtentReports createInstance(String fileName, String version) {
        ExtentSparkReporter reporter = new ExtentSparkReporter(fileName);
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle(fileName);
        reporter.config().setEncoding("utf-8");
        reporter.config().setReportName("App version:" + version);

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        return extent;
    }
}
