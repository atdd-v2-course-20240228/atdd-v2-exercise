package com.odde.atddv2;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.zh_cn.假如;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AndroidSteps {
    @SneakyThrows
    @假如("输入用户名{string}")
    public void 输入用户名(String name) {
        AndroidDriver<AndroidElement> driver;
        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("udid", "emulator-5558");
        caps.setCapability("platformVersion", "11.0");
        caps.setCapability("platformName", "Android");
        caps.setCapability("app", "/tmp/app.apk");
//        caps.setCapability("app", "/home/androiduser/app-debug.apk");
        caps.setCapability("remoteAdbHost", "anbox-mac");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
        driver.launchApp();

        TimeUnit.SECONDS.sleep(10);

        // Input username in edit

        driver.quit();
    }
}
