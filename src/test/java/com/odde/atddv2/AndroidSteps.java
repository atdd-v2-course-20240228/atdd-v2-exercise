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

    @Value("${appium.udid:emulator-5558}")
    private String udid;

    @SneakyThrows
    @假如("输入用户名{string}")
    public void 输入用户名(String name) {
        AndroidDriver<AndroidElement> driver;
        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("udid", udid);
        caps.setCapability("platformVersion", "11.0");
        caps.setCapability("platformName", "Android");
        caps.setCapability("app", "/tmp/app.apk");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
        driver.launchApp();

        var userNameInput = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\").text(\"用户名\")");
        userNameInput.sendKeys(name);

        TimeUnit.SECONDS.sleep(5);

        driver.quit();
    }
}
