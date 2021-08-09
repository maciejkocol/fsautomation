package com.automatedtest.fs;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/com/automatedtest/fs/FS.feature"},
        plugin = {"pretty",
        "json:target/cucumber_json_reports/fs.json",
        "html:target/fs-result.html"},
        glue = {"com.automatedtest.fs.infrastructure.driver",
                "com.automatedtest.fs.homepage"})
public class FSTest {
}
