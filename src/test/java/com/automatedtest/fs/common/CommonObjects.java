package com.automatedtest.fs.common;

import com.automatedtest.fs.infrastructure.driver.Setup;
import com.automatedtest.fs.infrastructure.driver.Wait;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CommonObjects {

    protected WebDriver driver;
    protected BrowserMobProxy proxy;
    protected Wait wait;

    public CommonObjects() {
        this.driver = Setup.driver;
        this.proxy = Setup.proxy;
        this.wait = new Wait(this.driver);
    }

    public void pause(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public List<String> getPageHeadings() {
        List<String> h2List = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.xpath("//h2"));
        for(WebElement element : elements) {
            h2List.add(element.getText());
        }
        return h2List;
    }

    public Map<String, String> getQueryParams(HarRequest harRequest) {
        HashMap<String, String> queryStringItems = new HashMap<>();
        harRequest.getQueryString()
                .forEach(item -> queryStringItems.put(item.getName(), item.getValue()));
        return queryStringItems;
    }

    public List<String> parseFSEvents() {
        List<String> parsedEventsList = new ArrayList<>();
        String orgId = "";
        String userId = "";
        String prevBundleTime = "";
        int seq = 0;

        // give fs enough time to log last event
        pause(5);

        // get all the https messages as Har responses
        final Har httpMessages = proxy.getHar();

        for (HarEntry httpMessage : httpMessages.getLog().getEntries()) {

            // check no errors on the XHR requests
            if (httpMessage.getRequest().getUrl().contains("fullstory.com/rec/bundle")) {

                // get request and response
                HarRequest request = httpMessage.getRequest();
                HarResponse response = httpMessage.getResponse();

                System.out.println();
                System.out.println("URL: " + httpMessage.getRequest().getUrl());

                // validate response status
                Assert.assertEquals("FS bundle response status", 200, response.getStatus());

                // retrieve request string parameters
                Map<String, String> params = getQueryParams(httpMessage.getRequest());

                // init
                if (seq == 0) {
                    orgId = params.get("OrgId");
                    userId = params.get("UserId");
                    prevBundleTime = "0";
                }
                seq++;

                // Display request parameters
                System.out.println("OrgId: " + params.get("OrgId"));
                System.out.println("UserId: " + params.get("UserId"));
                System.out.println("Seq: " + params.get("Seq"));
                System.out.println("PrevBundleTime: " + params.get("PrevBundleTime"));


                // validate request parameters
                Assert.assertEquals("OrgId param validation", orgId, params.get("OrgId"));
                Assert.assertEquals("UserId param validation", userId, params.get("UserId"));
                Assert.assertEquals("Seq param validation", Integer.toString(seq), params.get("Seq"));
                Assert.assertEquals("PrevBundleTime param validation", prevBundleTime, params.get("PrevBundleTime"));

                // get previous bundle time
                String responseBody = response.getContent().getText();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                if (jsonResponse.has("BundleTime")) {
                    prevBundleTime = jsonResponse.get("BundleTime").getAsString();
                }
                System.out.println("Response: " + jsonResponse);

                // get request body as json
                String requestBody = request.getPostData().getText();
                JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
                final JsonArray events = jsonObject.getAsJsonArray("Evts");

                // filter json for events
                for (JsonElement event : events) {
                    String kind = ((JsonObject) event).get("Kind").getAsString();
                    JsonArray argsArray = ((JsonObject) event).get("Args").getAsJsonArray();
                    if (!kind.isEmpty() && argsArray.size() > 1) {
                        String action = argsArray.get(0).getAsString();
                        String detail = argsArray.get(1).getAsString();

                        if (detail.isEmpty())
                            continue;

                        switch (kind) {
                            case "18":
                                parsedEventsList.add("fill : " + detail);
                                break;
                            default:
                                break;
                        }

                        switch (action) {
                            case "Product Added":
                                JsonObject jsonDetail = JsonParser.parseString(detail).getAsJsonObject();
                                if (jsonDetail.has("displayName_str")) {
                                    String val = jsonDetail.get("displayName_str").getAsString();
                                    parsedEventsList.add(action + " : " + val);
                                }
                                break;
                            case "Order Completed":
                                parsedEventsList.add(action);
                                break;
                            case "log":
                                detail = detail.replaceAll("\"", "");
                                parsedEventsList.add(action + " : " + detail);
                                break;
                            default:
                                break;
                        }
                    }
                }

            }
        }
        return parsedEventsList;
    }
}
