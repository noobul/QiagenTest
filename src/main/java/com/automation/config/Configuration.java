package com.automation.config;

public class Configuration {
    public static String TEST_URL = "http://www.hotwire.com/";
    public static String CHROME_BROWSER = "Chrome";
    public static final String[] BROWSER_ARGUMENTS = {"enable-automation", "--headless", "--no-sandbox",
            "--disable-extensions", "--dns-prefetch-disable"};

    public Configuration() {
    }

    public class TimeOuts{
        public static final int FIVE_SEC_WAIT = 5;
        public static final int SIXTY_SEC_WAIT = 60;
        public static final int THREE_MIN_WAIT = 180;
    }

    public class LogType{
        public static final String  PASS = "Pass";
        public static final String  FAIL = "Fail";
        public static final String  WARNING = "Warning";
        public static final String  INFO = "Info";
        public static final String SKIP = "Skip";
    }

}
