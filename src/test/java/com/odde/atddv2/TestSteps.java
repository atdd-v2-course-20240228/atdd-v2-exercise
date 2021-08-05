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

    @假如("存在用户名为\"joseph\"和密码为\"{int}\"的用户")
    public void 存在用户名为_joseph_和密码为_的用户(Integer int1) {
    }

    @当("通过API以用户名为\"joseph\"和密码为\"{int}\"登录时")
    public void 通过api以用户名为_joseph_和密码为_登录时(Integer int1) {
    }

    @那么("打印Token")
    public void 打印_token() {
    }

    @当("在百度搜索关键字\"cucumber\"")
    public void 在百度搜索关键字_cucumber() {
    }

    @那么("打印百度为您找到的相关结果数")
    public void 打印百度为您找到的相关结果数() {
    }

    private WebDriver getWebDriver() {
        if (webDriver == null)
            webDriver = createWebDriver();
        return webDriver;
    }
}
