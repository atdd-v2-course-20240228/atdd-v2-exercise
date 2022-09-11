package com.odde.atddv2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.leeonky.cucumber.restful.RestfulStep;
import com.odde.atddv2.entity.User;
import com.odde.atddv2.repo.UserRepo;
import io.cucumber.docstring.DocString;
import io.cucumber.java.Before;
import io.cucumber.java.zh_cn.并且;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class ApiOrderSteps {

    @Autowired
    MockServer mockServer;

    @Value("${binstd-endpoint.key}")
    private String binstdAppKey;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestfulStep restfulStep;

    @PostConstruct
    public void setBaseUrl() {
        restfulStep.setBaseUrl("http://localhost:10081/api");
    }

    @SneakyThrows
    @Before("@api-login")
    public void apiLogin() {
        User defaultUser = new User().setUserName("j").setPassword("j");
        userRepo.save(defaultUser);
        ObjectMapper objectMapper = new ObjectMapper();
        restfulStep.setBaseUrl("http://localhost:10081");
        restfulStep.post("/users/login", DocString.create(objectMapper.writeValueAsString(defaultUser)));
        String token = restfulStep.responseShouldBe("headers.Token");
        restfulStep.header("token", token);
        restfulStep.setBaseUrl("http://localhost:10081/api");
    }

    @并且("存在快递单{string}的物流信息如下")
    public void 存在快递单的物流信息如下(String deliverNo, String json) {
        mockServer.getJson("/express/query", (request) -> request.withQueryStringParameter("appkey", binstdAppKey)
                .withQueryStringParameter("type", "auto")
                .withQueryStringParameter("number", deliverNo), json);
    }

}
