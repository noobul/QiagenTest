package com.automation.utils;

import org.testng.annotations.DataProvider;

public class DProvider {

    @DataProvider(name = "firstTest")
    public static Object[][] firstTest(){
        return new Object[][]{{"San Francisco, CA, United States of America (SFO-San Francisco Intl.)",
                "Los Angeles, CA, United States of America (LAX-Los Angeles Intl.)"}};
    }
}
