package com.odde.atddv2;

import com.github.leeonky.cucumber.restful.RestfulStep;
import com.odde.atddv2.repo.OrderRepo;
import com.odde.atddv2.repo.UserRepo;
import io.cucumber.java.Before;
import io.cucumber.java.zh_cn.当;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.PostConstruct;

@ContextConfiguration(classes = {CucumberConfiguration.class}, loader = SpringBootContextLoader.class)
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApplicationSteps {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Before(order = 1)
    public void clearDB() {
        userRepo.deleteAll();
        orderRepo.deleteAll();
    }

    @Autowired
    private RestfulStep restfulStep;

    @PostConstruct
    public void setBaseUrl() {
        restfulStep.setBaseUrl("http://127.0.0.1:10081/api/");
    }

    @当("等待{int}秒")
    public void 等待_秒(long s) throws InterruptedException {
        Thread.sleep(s * 1000);
    }
}
