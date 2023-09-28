package com.odde.atddv2;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.URL;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Component
public class App {

    @Value("${appium.udid:emulator-5554}")
    private String udid;
    private AndroidDriver<AndroidElement> driver;

    private AndroidDriver<AndroidElement> getAndroidDriver() {
        if (driver == null)
            driver = createAndroidDriver();
        return driver;
    }

    @SneakyThrows
    private AndroidDriver<AndroidElement> createAndroidDriver() {
        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("udid", udid);
        caps.setCapability("platformVersion", "11.0");
        caps.setCapability("platformName", "Android");
        caps.setCapability("app", "/tmp/app.apk");
        return new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
    }

    public void launch() {
        getAndroidDriver().launchApp();
    }

    public void inputTextByHint(String hint, String text) {
        await().ignoreExceptions().untilAsserted(() -> waitElementByEditTextHint(hint).sendKeys(text));
    }

    private AndroidElement waitElementByEditTextHint(String hint) {
        return await().ignoreExceptions().until(() -> getAndroidDriver().findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\").text(\"" + hint + "\")"), Objects::nonNull);
    }

    private AndroidElement waitElementByText(String text) {
        return await().ignoreExceptions().until(() -> getAndroidDriver().findElementByAndroidUIAutomator(String.format("new UiSelector().text(\"%s\")", text)), Objects::nonNull);
    }

    public void clickByText(String text) {
        await().ignoreExceptions().untilAsserted(() -> waitElementByText(text).click());
    }

    public void shouldHaveText(String text) {
        await().ignoreExceptions().untilAsserted(() -> assertThat(getAndroidDriver().findElementsByAndroidUIAutomator(String.format("new UiSelector().text(\"%s\")", text))).isNotEmpty());
    }

    public void closeApp() {
        getAndroidDriver().closeApp();
    }

    @PreDestroy
    public void quit() {
        getAndroidDriver().quit();
    }

}
