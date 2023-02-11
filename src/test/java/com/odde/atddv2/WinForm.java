package com.odde.atddv2;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.openqa.selenium.By.xpath;

@Component
public class WinForm {

    private WindowsDriver<WindowsElement> windowsDriver = null;
    private String currentWindow;
    @Value("${app.path:C:\\ClientServerProject\\软件系统客户端模版\\bin\\Debug\\软件系统客户端模版.exe}")
    private String appPath;

    public void inputTextById(String id, String text) {
        waitElement("//*[@AutomationId='" + id + "']").sendKeys(text);
    }

    public void clickById(String id) {
        waitElement("//*[@AutomationId='" + id + "']").click();
    }

    public void shouldHaveText(String text) {
        await().ignoreExceptions().untilAsserted(() -> assertThat(getWindowsDriver().findElements(xpath("//*[@Name='" + text + "']"))).isNotEmpty());
    }

    public void close() {
        if (windowsDriver != null) {
            windowsDriver.quit();
            windowsDriver = null;
        }
    }

    @SneakyThrows
    public WindowsDriver<WindowsElement> createWinDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", appPath);
        return new WindowsDriver<>(new URL("http://127.0.0.1:4723"), capabilities);
    }

    public WebDriver getWindowsDriver() {
        if (windowsDriver == null)
            windowsDriver = createWinDriver();
        currentWindow = getCurrentWindow();
        return windowsDriver;
    }

    @SneakyThrows
    public void newPage() {
        await().ignoreExceptions().untilAsserted(() -> assertThat(currentWindow).isNotEqualTo(getCurrentWindow()));
        currentWindow = getCurrentWindow();
        windowsDriver.switchTo().window(currentWindow);
    }

    private String getCurrentWindow() {
        return windowsDriver.getWindowHandles().iterator().next();
    }

    private WebElement waitElement(String xpathExpression) {
        return await().ignoreExceptions().until(() -> getWindowsDriver().findElement(xpath(xpathExpression)), Objects::nonNull);
    }
}
