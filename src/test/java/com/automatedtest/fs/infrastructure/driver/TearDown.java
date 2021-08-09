package com.automatedtest.fs.infrastructure.driver;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import net.lightbody.bmp.BrowserMobProxy;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class TearDown {

    final WebDriver driver;
    final BrowserMobProxy proxy;

    public TearDown() {
        this.driver = Setup.driver;
        this.proxy = Setup.proxy;
    }

    @After
    public void quitDriver(Scenario scenario) {
        if(scenario.isFailed()){
           saveScreenshotsForScenario(scenario);
        }
        this.proxy.abort();
        this.driver.quit();
    }

    @AfterStep
    public void waitASecond() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    private void saveScreenshotsForScenario(final Scenario scenario) {

        final byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName());
    }
}
