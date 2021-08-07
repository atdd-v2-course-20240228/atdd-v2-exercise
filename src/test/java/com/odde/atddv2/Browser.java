package com.odde.atddv2;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.URL;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.openqa.selenium.By.xpath;

@Component
public class Browser {

    private WebDriver webDriver = null;

    @SneakyThrows
    private WebDriver createWebDriver() {
        return new RemoteWebDriver(new URL("http://web-driver.tool.net:4444"), DesiredCapabilities.chrome());
    }

    public void launchByUrl(String path) {
        getWebDriver().get("http://host.docker.internal:10081" + path);
    }

    private WebDriver getWebDriver() {
        if (webDriver == null)
            webDriver = createWebDriver();
        return webDriver;
    }

    public void inputTextByPlaceholder(String placeholder, String text) {
        waitElement("//*[@placeholder='" + placeholder + "']").sendKeys(text);
    }

    public void clickByText(String text) {
        waitElement(String.format("//*[normalize-space(@value)='%s' or normalize-space(text())='%s']", text, text)).click();
    }

    public void shouldHaveText(String text) {
        await().untilAsserted(() -> assertThat(getWebDriver().findElements(xpath("//*[text()='" + text + "']"))).isNotEmpty());
    }

    public void selectTextByPlaceholder(String placeholder, String text) {
        waitElement(String.format("//*[normalize-space(@placeholder)='%s']", placeholder)).click();
        clickByText(text);
    }

    public void shouldNotHaveText(String text) {
        await().untilAsserted(() -> assertThat(getWebDriver().findElements(xpath("//*[text()='" + text + "']"))).isEmpty());
    }

    @PreDestroy
    public void close() {
        if (webDriver != null) {
            webDriver.close();
            webDriver = null;
        }
    }

    private WebElement waitElement(String xpathExpression) {
        return await().until(() -> getWebDriver().findElement(xpath(xpathExpression)), Objects::nonNull);
    }
}
