package com.odde.atddv2;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.After;
import io.cucumber.java.zh_cn.假如;
import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_cn.那么;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class AndroidSteps {

    @Value("${appium.udid:emulator-5554}")
    private String udid;
    private AndroidDriver<AndroidElement> driver;

    @SneakyThrows
    @假如("输入用户名{string}")
    public void 输入用户名(String name) {
        AndroidDriver<AndroidElement> driver = getAndroidDriver();
        driver.launchApp();

        var userNameInput = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\").text(\"用户名\")");
        userNameInput.sendKeys(name);

        TimeUnit.SECONDS.sleep(5);

        driver.quit();
    }

    @After
    public void closeApp() {
        if (driver != null) {
            driver.closeApp();
            driver.quit();
            driver = null;
        }
    }

    private AndroidDriver<AndroidElement> getAndroidDriver() {
        if (driver == null)
            driver = createWebDriver();
        return driver;
    }

    @SneakyThrows
    private AndroidDriver<AndroidElement> createWebDriver() {
        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("udid", udid);
        caps.setCapability("platformName", "Android");
        caps.setCapability("app", "/tmp/app.apk");
        return new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
    }

    @当("以用户名为{string}和密码为{string}登录时")
    public void 以用户名为和密码为登录时(String userName, String password) {
        getAndroidDriver().launchApp();
        await().ignoreExceptions().until(() -> getAndroidDriver().findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\").text(\"用户名\")"), Objects::nonNull).sendKeys(userName);
        await().ignoreExceptions().until(() -> getAndroidDriver().findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\").text(\"密码\")"), Objects::nonNull).sendKeys(password);
        await().ignoreExceptions().until(() -> getAndroidDriver().findElementByAndroidUIAutomator("new UiSelector().text(\"登录\")"), Objects::nonNull).click();
    }

    @那么("{string}登录成功")
    public void 登录成功(String userName) {
        await().ignoreExceptions().untilAsserted(() -> assertThat(getAndroidDriver().findElementsByAndroidUIAutomator("new UiSelector().text(\"Welcome " + userName + "\")")).isNotEmpty());
    }

    @那么("登录失败的错误信息是{string}")
    public void 登录失败的错误信息是(String message) {
        await().ignoreExceptions().untilAsserted(() -> assertThat(getAndroidDriver().findElementsByAndroidUIAutomator("new UiSelector().text(\"" + message + "\")")).isNotEmpty());
    }

}
