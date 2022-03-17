package com.odde.atddv2;

import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.openqa.selenium.By.xpath;

@Component
public class WinForm {

    static {
        Awaitility.setDefaultTimeout(30, SECONDS);
    }

    private WiniumDriver windowsDriver = null;
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
        await().untilAsserted(() -> assertThat(getWindowsDriver().findElements(xpath("//*[@Name='" + text + "']"))).isNotEmpty());
    }

    public void close() {
        if (windowsDriver != null) {
            windowsDriver.quit();
            windowsDriver = null;
        }
    }

    @SneakyThrows
    public WiniumDriver createWinDriver() {
        DesktopOptions options = new DesktopOptions();
        options.setApplicationPath(appPath);
        options.setDebugConnectToRunningApp(false);
        options.setLaunchDelay(2);
        return new WiniumDriver(new URL("http://127.0.0.1:9999"), options);
    }

    public WebDriver getWindowsDriver() {
        if (windowsDriver == null)
            windowsDriver = createWinDriver();
        currentWindow = getCurrentWindow();
        return windowsDriver;
    }

    @SneakyThrows
    public void newPage() {
        await().untilAsserted(() -> assertThat(currentWindow).isNotEqualTo(getCurrentWindow()));
        currentWindow = getCurrentWindow();
        windowsDriver.switchTo().window(currentWindow);
    }

    private String getCurrentWindow() {
        return windowsDriver.getWindowHandle();
    }

    private WebElement waitElement(String xpathExpression) {
        return await().until(() -> getWindowsDriver().findElement(xpath(xpathExpression)), Objects::nonNull);
    }
}
