package com.automatedtest.fs.infrastructure.driver;

import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class Setup {

    public static WebDriver driver;
    public static BrowserMobProxy proxy;

    @Before
    public void setDriverAndProxy() {

        boolean headless = Boolean.parseBoolean(System.getProperty("headless"));

        // start the proxy
        proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.start(0);

        // get the selenium proxy
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        seleniumProxy.setSslProxy("localhost:" + proxy.getPort());

        // add chrome options to ignore certificate errors for https
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--window-size=1680,1050");

        // set headless options
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        }

        // set the selenium proxy
        options.setProxy(seleniumProxy);
        options.setAcceptInsecureCerts(true);

        // set up chromedriver and properties
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.args", "--disable-logging");

        // prepare chromedriver service required for latest selenium
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .build();

        // setup driver
        driver = new ChromeDriver(service, options);

        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        // create a new HAR with the label "fs"
        proxy.newHar("fs-capture");
    }
}
