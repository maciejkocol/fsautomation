package com.automatedtest.fs.homepage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class FSPageSteps {

    final FSPage FSPage;

    public FSPageSteps() {
        this.FSPage = new FSPage();
    }

    @Given("^A user navigates to page \"([^\"]*)\"$")
    public void aUserNavigatesToPage(String url) {
        this.FSPage.goToPage(url);
    }

    @When("^Adds \"([^\"]*)\" to cart$")
    public void addToCart(String item) {
        this.FSPage.addToCart(item);
    }

    @When("^Adds featured \"([^\"]*)\" to cart$")
    public void addFeaturedToCart(String item) {
        this.FSPage.addFeaturedToCart(item);
    }

    @When("^Fills \"([^\"]*)\" as \"([^\"]*)\"$")
    public void fillField(String name, String text) {
        this.FSPage.fillField(name, text, "1");
    }

    @When("^Fills \"([^\"]*)\" instance of \"([^\"]*)\" as \"([^\"]*)\"$")
    public void fillField(String seq, String name, String text) {
        this.FSPage.fillField(name, text, seq);
    }

    @When("^Makes shipping same as billing$")
    public void makeShippingSameBilling() {
        this.FSPage.makeShippingSameBilling();
    }

    @When("^Agrees to purchase$")
    public void agreeToPurchase() {
        this.FSPage.agreeToPurchase();
    }

    @When("^Makes purchase$")
    public void makePurchase() {
        this.FSPage.makePurchase();
    }

    @When("^Goes to cart$")
    public void goToCart() {
        this.FSPage.goToCart();
    }

    @When("^Goes to checkout")
    public void goToCheckout() {
        this.FSPage.goToCheckout();
    }

    @Then("^Confirm order received$")
    public void confirmOrderReceived() {
        List<String> pageHeadings = this.FSPage.getPageHeadings();
        Assert.assertTrue("Order received", pageHeadings.contains("ORDER RECEIVED!"));

        this.FSPage.addEvent("Order Completed");
        this.FSPage.addEvent("log : USER is going to /confirm");
    }

    @Then("^Validate FS captured data$")
    public void validateHarData() {
        List<String> parsedFSEvents = this.FSPage.parseFSEvents();
        List<String> storedATEvents = this.FSPage.getAutomationEvents();

        Assert.assertEquals("FS captured the right data", storedATEvents, parsedFSEvents);
    }
}
