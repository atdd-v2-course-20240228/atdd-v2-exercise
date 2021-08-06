package com.odde.atddv2;

import io.cucumber.java.After;
import io.cucumber.java.zh_cn.假如;
import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_cn.那么;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.xpath;

public class TestSteps {
    private WebDriver webDriver = null;

    @SneakyThrows
    public WebDriver createWebDriver() {
        return new RemoteWebDriver(new URL("http://web-driver.tool.net:4444"), DesiredCapabilities.chrome());
    }

    @After
    public void closeBrowser() {
        getWebDriver().quit();
    }

    @当("测试环境")
    public void 测试环境() {
        getWebDriver().get("http://host.docker.internal:10081/");
        assertThat(getWebDriver().findElements(xpath("//*[text()='登录']"))).isNotEmpty();
        getWebDriver().quit();
    }

    @那么("打印Token")
    public void 打印_token() {
    }

    @那么("打印百度为您找到的相关结果数")
    public void 打印百度为您找到的相关结果数() {
    }

    @假如("存在用户名为{string}和密码为{string}的用户")
    public void 存在用户名为和密码为的用户(String arg0, String arg1) {
    }

    @当("通过API以用户名为{string}和密码为{string}登录时")
    public void 通过api以用户名为和密码为登录时(String arg0, String arg1) {
    }

    @当("在百度搜索关键字{string}")
    public void 在百度搜索关键字(String arg0) {
    }

    private WebDriver getWebDriver() {
        if (webDriver == null)
            webDriver = createWebDriver();
        return webDriver;
    }
}
