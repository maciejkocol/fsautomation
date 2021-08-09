package com.automatedtest.fs.homepage;

import com.automatedtest.fs.common.CommonObjects;

import java.text.MessageFormat;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static com.automatedtest.fs.common.CommonConstants.*;

public class FSPage extends CommonObjects {

    final static List<String> automationEvents = new ArrayList<>();

    FSPage() {
        PageFactory.initElements(driver, this);
    }

    public void goToPage(String url) {
        driver.get(url);
        wait.forLoad(10);
    }

    public void addEvent(String event) {
        automationEvents.add(event);
    }

    public void addFeaturedToCart(String itemName) {
        String xpath = MessageFormat.format(featuredItemByNameXpath, itemName);
        wait.forElement(xpath, 10);
        driver.findElement(By.xpath(xpath)).click();

        // add event(s)
        addEvent("Product Added : " + itemName);
        addEvent("log : " + itemName + " embiggen user cart.");
        addEvent("log : " + "USER is going to /market");
    }

    public void addToCart(String itemName) {
        String xpath = MessageFormat.format(itemByNameXpath, itemName);
        wait.forElement(xpath, 10);
        driver.findElement(By.xpath(xpath)).click();

        // add event(s)
        addEvent("Product Added : " + itemName);
        addEvent("log : " + itemName + " embiggen user cart.");
    }

    public void goToCart() {
        String xpath = myCartXpath;
        wait.forElement(xpath, 10);
        driver.findElement(By.xpath(xpath)).click();

        // add event(s)
        addEvent("log : " + "USER is going to /cart");
    }

    public void goToCheckout() {
        String xpath = checkoutXpath;
        wait.forElement(xpath, 10);
        driver.findElement(By.xpath(xpath)).click();

        // add event(s)
        addEvent("log : " + "USER is going to /checkout");
    }

    public void fillField(String name, String text, String seq) {
        seq = seq.replaceAll("[^0-9]", "");
        String xpath = MessageFormat.format(inputFieldXpath, name, seq);
        wait.forElement(xpath, 10);
        WebElement inputFieldElement = driver.findElement(By.xpath(xpath));
        inputFieldElement.sendKeys(text);

        // add event(s)
        // exclude some for security reasons
        switch (name) {
            case "Credit Card Number":
            case "Password":
                break;
            default:
                StringBuilder fillStr = new StringBuilder();
                for (char c : text.toCharArray()) {
                    fillStr.append(c);
                    addEvent("fill : " + fillStr);
                }
                addEvent("fill : " + text);
                break;
        }
    }

    public void makeShippingSameBilling() {
        String xpath = shippingSameBillingXpath;
        wait.forElement(xpath, 10);
        driver.findElement(By.xpath(xpath)).click();

        // add event(s)
        addEvent("fill : true");
    }

    public void agreeToPurchase() {
        String xpath = agreeToPurchaseXpath;
        wait.forElement(xpath, 10);
        driver.findElement(By.xpath(xpath)).click();

        // add event(s)
        addEvent("fill : true");
    }

    public void makePurchase() {
        String xpath = makePurchaseXpath;
        wait.forElement(xpath, 10);
        driver.findElement(By.xpath(xpath)).click();
    }

    public List<String> getAutomationEvents() {
        return automationEvents;
    }
}
