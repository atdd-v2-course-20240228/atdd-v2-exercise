package com.odde.atddv2;

import com.odde.atddv2.entity.User;
import com.odde.atddv2.page.HomePage;
import com.odde.atddv2.repo.UserRepo;
import io.cucumber.java.Before;
import io.cucumber.java.zh_cn.假如;
import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_cn.那么;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginSteps {

    @Autowired
    private HomePage homePage;

    @Autowired
    private App app;

    @Autowired
    private UserRepo userRepo;

    @假如("存在用户名为{string}和密码为{string}的用户")
    public void 存在用户名为和密码为的用户(String userName, String password) {
        userRepo.save(new User().setUserName(userName).setPassword(password));
    }

    @当("以用户名为{string}和密码为{string}登录时")
    public void 以用户名为和密码为登录时(String userName, String password) {
        homePage.open();
        homePage.login(userName, password);
    }

    @那么("{string}登录成功")
    public void 登录成功(String userName) {
        app.shouldHaveText("Welcome: " + userName);
    }

    @那么("登录失败的错误信息是{string}")
    public void 登录失败的错误信息是(String message) {
        app.shouldHaveText(message);
    }

    @Before("@ui-login")
    public void uiLogin() {
        存在用户名为和密码为的用户("j", "j");
        以用户名为和密码为登录时("j", "j");
        登录成功("j");
    }
}
