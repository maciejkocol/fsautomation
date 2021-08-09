## FS Automation in Java with Cucumber and Selenium WebDriver ##

This project uses [BrowserMob Proxy](https://github.com/lightbody/browsermob-proxy) to check if data is getting properly captured from a page and sent to FS servers.

Test scenarios are defined in the feature files at `./src/test/resources/com/automatedtest/fs`

Installation
------------

### Setup
```console
# Clone this repo
https://github.com/maciejkocol/fsautomation

# Change directory into the cloned repo
cd fsautomation

# Install
mvn clean
```

## Run tests ##
```console
mvn test
```

To run in headless mode, add `-Dheadless={bool}` where `{bool}` is either `true` or `false`. 

Sample test run:

```console
mvn test -Dheadless=true
```

## Results ##

Obtain detailed html report at `./target/fs-result.html`

