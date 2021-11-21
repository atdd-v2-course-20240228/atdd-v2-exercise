package com.odde.atddv2;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import io.cucumber.java.After;
import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_cn.那么;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class WinSteps {

    private WindowsDriver<WindowsElement> winDriver = null;

    public WindowsDriver<WindowsElement> getWinDriver() {
        if (winDriver == null)
            winDriver = createWinDriver();
        return winDriver;
    }

    @当("使用计算器计算{int}+{int}时")
    public void 使用计算器计算时(int first, int second) {
        System.out.println("getWinDriver().getPageSource() = " + getWinDriver().getPageSource());
        getWinDriver().findElementByName("Four").click();
    }

    @SneakyThrows
    @那么("结果为{int}")
    public void 结果为(int result) {
    }

    @After
    public void close() {
        if (winDriver != null) {
            winDriver.quit();
            winDriver = null;
        }
    }

    @SneakyThrows
    private WindowsDriver<WindowsElement> createWinDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
        return new WindowsDriver<>(new URL("http://127.0.0.1:4723"), capabilities);
    }

}
