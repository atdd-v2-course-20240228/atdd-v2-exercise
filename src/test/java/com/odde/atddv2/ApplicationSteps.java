package com.odde.atddv2;

import com.odde.atddv2.entity.User;
import com.odde.atddv2.page.HomePage;
import com.odde.atddv2.page.WelcomePage;
import com.odde.atddv2.repo.UserRepo;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.zh_cn.假如;
import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_cn.那么;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {CucumberConfiguration.class}, loader = SpringBootContextLoader.class)
@CucumberContextConfiguration
public class ApplicationSteps {

    @Autowired
    private HomePage homePage;

    @Autowired
    private WelcomePage welcomePage;

    @Autowired
    private WinForm winForm;

    @Autowired
    private UserRepo userRepo;

    @假如("存在用户名为{string}和密码为{string}的用户")
    public void 存在用户名为和密码为的用户(String userName, String password) {
        userRepo.save(new User().setUserName(userName).setPassword(password));
    }

    @当("以用户名为{string}和密码为{string}登录时")
    public void 以用户名为和密码为登录时(String userName, String password) {
        homePage.login(userName, password);
    }

    @那么("{string}登录成功")
    public void 登录成功(String userName) {
        welcomePage.open();
        welcomePage.loginUserShouldBe(userName);
    }

    @那么("登录失败的错误信息是{string}")
    public void 登录失败的错误信息是(String message) {
        homePage.shouldHaveMessage(message);
    }

    @Before(order = 1)
    public void clearDB() {
        userRepo.deleteAll();
    }

    @After
    public void close() {
        winForm.close();
    }

    @Before("@ui-login")
    public void uiLogin() {
        存在用户名为和密码为的用户("j", "j");
        以用户名为和密码为登录时("j", "j");
        登录成功("j");
    }
}
