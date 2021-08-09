package com.automatedtest.fs.common;

public final class CommonConstants {

    // add to cart
    public static String featuredItemByNameXpath = "//div[starts-with(@class,\"featured-fruit\") and contains(.,\"{0}\")]//a[@ng-click=\"addToCart(fruit)\"]";
    public static String itemByNameXpath = "//div[starts-with(@class,\"fruit-box\") and contains(.,\"{0}\")]//a[@ng-click=\"addToCart(fruit)\"]";

    // my cart
    public static String myCartXpath = "//a[@href=\"#/cart\"]";

    // checkout
    public static String checkoutXpath = "//a[text()=\"Checkout\"]";
    public static String inputFieldXpath = "(//input[preceding-sibling::label[text()=\"{0}\"]])[{1}]";
    public static String shippingSameBillingXpath = "//input[@id=\"shipping-same-billing\"]";
    public static String agreeToPurchaseXpath = "//input[@id=\"im-sure-check\"]";
    public static String makePurchaseXpath = "//a[text()=\"Purchase\"]";

    private CommonConstants() {
        throw new AssertionError();
    }
}
