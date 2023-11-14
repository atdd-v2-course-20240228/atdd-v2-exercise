package com.odde.atddv2;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.zh_cn.假如;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AndroidSteps {

    @Value("${appium.udid:emulator-5554}")
    private String udid;

    @SneakyThrows
    @假如("输入用户名{string}")
    public void 输入用户名(String name) {
        AndroidDriver<AndroidElement> driver;
        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("udid", udid);
        caps.setCapability("platformName", "Android");
        caps.setCapability("app", "/tmp/app.apk");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
        driver.launchApp();

        TimeUnit.SECONDS.sleep(10);

        // Input username in edit

        driver.quit();
    }
}
