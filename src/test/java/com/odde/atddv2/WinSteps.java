package com.odde.atddv2;

import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_cn.那么;
import lombok.SneakyThrows;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;

import java.net.URL;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class WinSteps {

    private static final String[] buttonNames = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    private WiniumDriver winDriver = null;

    public WiniumDriver getWinDriver() {
        if (winDriver == null)
            winDriver = createWinDriver();
        return winDriver;
    }

    @当("使用计算器计算{int}+{int}时")
    public void 使用计算器计算时(int first, int second) {
        System.out.println("getWinDriver().getPageSource() = " + getWinDriver().getPageSource());
        getWinDriver().findElementByName(buttonNames[first]).click();
        getWinDriver().findElementByName("Plus").click();
        getWinDriver().findElementByName(buttonNames[second]).click();
        getWinDriver().findElementByName("Equals").click();
    }

    @SneakyThrows
    @那么("结果为{int}")
    public void 结果为(int result) {
        System.out.println("getWinDriver().getPageSource() = " + getWinDriver().getPageSource());
        SECONDS.sleep(1);
        assertThat(getWinDriver().findElementById("CalculatorResults").getText()).isEqualTo("Display is " + result);
    }

//    @After
//    public void close() {
//        if (winDriver != null) {
//            winDriver.quit();
//            winDriver = null;
//        }
//    }

    @SneakyThrows
    private WiniumDriver createWinDriver() {
        DesktopOptions options = new DesktopOptions();
        options.setApplicationPath("Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
        options.setDebugConnectToRunningApp(false);
        options.setLaunchDelay(2);
        return new WiniumDriver(new URL("http://127.0.0.1:9999"), options);
    }

}
