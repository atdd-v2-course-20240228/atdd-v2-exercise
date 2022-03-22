package com.odde.atddv2;

import Demo.ClockPrx;
import Demo.LoginResponse;
import Demo.TimeOfDay;
import com.github.leeonky.jfactory.JFactory;
import com.odde.atddv2.ice.ClockI;
import com.odde.atddv2.ice.LoginService;
import com.odde.atddv2.ice.PrinterI;
import com.odde.atddv2.ice.spec.TimeOfDays;
import com.odde.atddv2.page.HomePage;
import com.odde.atddv2.page.WelcomePage;
import com.odde.atddv2.repo.UserRepo;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.zh_cn.假如;
import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_cn.那么;
import io.cucumber.spring.CucumberContextConfiguration;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

import static com.github.leeonky.dal.extension.assertj.DALAssert.expect;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CucumberConfiguration.class}, loader = SpringBootContextLoader.class)
@CucumberContextConfiguration
public class ApplicationSteps {

    @Autowired
    JFactory jFactory;
    private String response;
    private TimeOfDay timeOfDayResponse;
    private Communicator communicator = Util.initialize();
    private PrinterI printerI = spy(new PrinterI());
    private ClockI clockI = spy(new ClockI());
    private LoginService loginService = spy(new LoginService());
    @Autowired
    private HomePage homePage;
    @Autowired
    private WelcomePage welcomePage;
    @Autowired
    private WinForm winForm;
    @Autowired
    private UserRepo userRepo;

    @Before
    public void startIceMockServer() {
        new Thread(() -> {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("MockServer", "default -p 10000");
            adapter.add(printerI, Util.stringToIdentity("PrinterI"));
            adapter.add(clockI, Util.stringToIdentity("ClockI"));
            adapter.add(loginService, Util.stringToIdentity("LoginService"));
            adapter.activate();
            communicator.waitForShutdown();
        }).start();
    }

    @After
    public void stopIceMockServer() {
        communicator.shutdown();
    }

    @When("ice client send request")
    public void iceClientSendRequest() {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize()) {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("PrinterI:default -p 10000");
            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(base);
            if (printer == null) {
                throw new Error("Invalid proxy");
            }
            response = printer.printString("Hello world");
        }
    }

    @Given("ice mock server with response {string}")
    public void iceMockServerWithResponse(String response) {
        when(printerI.printString(anyString(), any(Current.class))).thenReturn(response);
    }

    @Then("ice client get server response {string}")
    public void iceClientGetServerResponse(String expected) {
        Assertions.assertThat(response).isEqualTo(expected);
    }

    @When("ice client send get time request")
    public void iceClientSendGetTimeRequest() {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize()) {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("ClockI:default -p 10000");
            ClockPrx clock = ClockPrx.checkedCast(base);
            if (clock == null) {
                throw new Error("Invalid proxy");
            }
            timeOfDayResponse = clock.getTime();
        }
    }

    @Then("ice client get server response structure")
    public void iceClientGetServerResponseStructure(String expression) {
        expect(timeOfDayResponse).should(expression);
    }

    @Given("ice mock server with for object {string}")
    public void iceMockServerWithForObject(String objectName) {
        when(clockI.getTime(any(Current.class))).thenReturn(jFactory.spec(TimeOfDays.TimeOfDay.class).query());
    }

    @假如("存在用户名为{string}和密码为{string}的用户")
    public void 存在用户名为和密码为的用户(String userName, String password) {
//        userRepo.save(new User().setUserName(userName).setPassword(password));
        when(loginService.login(argThat(argument -> argument.userName.equals(userName) && argument.password.equals(password)), any(Current.class)))
                .thenReturn(new LoginResponse(200));
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
