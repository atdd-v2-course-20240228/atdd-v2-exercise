package com.odde.atddv2;

import com.odde.atddv2.entity.User;
import com.odde.atddv2.repo.UserRepo;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import io.cucumber.java.After;
import io.cucumber.java.zh_cn.假如;
import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_cn.那么;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.openqa.selenium.By.xpath;

public class TestSteps {
    @Autowired
    UserRepo userRepo;

    @Value("${app.path:C:\\ClientServerProject\\软件系统客户端模版\\bin\\Debug\\软件系统客户端模版.exe}")
    private String appPath;

    private WindowsDriver<WindowsElement> winDriver = null;

    public WindowsDriver<WindowsElement> getWinDriver() {
        if (winDriver == null)
            winDriver = createWinDriver();
        return winDriver;
    }

    @After
    public void close() {
        if (winDriver != null) {
            winDriver.quit();
            winDriver = null;
        }
    }

    @假如("存在用户名为{string}和密码为{string}的用户")
    public void 存在用户名为和密码为的用户(String userName, String password) {
        userRepo.deleteAll();
        userRepo.save(new User().setUserName(userName).setPassword(password));
    }

    @当("以用户名为{string}和密码为{string}登录时")
    public void 以用户名为和密码为登录时(String userName, String password) {
        await().until(() -> getWinDriver().findElement(xpath("//*[@AutomationId='textBox_userName']")), Objects::nonNull).sendKeys(userName);
        await().until(() -> getWinDriver().findElement(xpath("//*[@AutomationId='textBox_password']")), Objects::nonNull).sendKeys(password);
        await().until(() -> getWinDriver().findElement(xpath("//*[@AutomationId='userButton_login']")), Objects::nonNull).click();
    }

    @SneakyThrows
    @那么("{string}登录成功")
    public void 登录成功(String userName) {
        TimeUnit.SECONDS.sleep(5);
        getWinDriver().switchTo().window(getWinDriver().getWindowHandles().iterator().next());
        await().untilAsserted(() -> assertThat(getWinDriver().findElements(xpath("//*[@Name='" + ("Welcome " + userName) + "']"))).isNotEmpty());
    }

    @那么("登录失败的错误信息是{string}")
    public void 登录失败的错误信息是(String message) {
        await().untilAsserted(() -> assertThat(getWinDriver().findElements(xpath("//*[@Name='" + message + "']"))).isNotEmpty());
    }

    @SneakyThrows
    private WindowsDriver<WindowsElement> createWinDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", appPath);
        return new WindowsDriver<>(new URL("http://127.0.0.1:4723"), capabilities);
    }
}
