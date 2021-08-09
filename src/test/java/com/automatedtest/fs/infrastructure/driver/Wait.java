package com.automatedtest.fs.infrastructure.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {

    final WebDriver driver;

    public Wait(WebDriver driver) {
        this.driver = driver;
    }

    private void waitUntil(ExpectedCondition<?> condition, String message, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.withMessage(message);
        wait.until(condition);
    }

    public void forElement(String locator, int timeout){
        ExpectedCondition<WebElement> condition = ExpectedConditions.visibilityOfElementLocated(By.xpath(locator));
        String timeoutMessage = "Element took more than " + timeout + " seconds to find.";
        waitUntil(condition, timeoutMessage, timeout);
    }

    public void forLoad(int timeout){
        ExpectedCondition<Object> condition = ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";");
        String timeoutMessage = "Page took more than " + timeout + " seconds to load.";
        waitUntil(condition, timeoutMessage, timeout);
    }
}
