package com.odde.atddv2;

import com.github.leeonky.cucumber.restful.RestfulStep;
import io.cucumber.java.zh_cn.那么;
import lombok.SneakyThrows;

public class RelationsStep {

    @SneakyThrows
    @那么("API{string}应为:")
    public void api应为(String url, String expression) {
        RestfulStep restfulStep = new RestfulStep();
        restfulStep.setBaseUrl("http://localhost:8080");
        restfulStep.getAndResponseShouldBe(url, expression);
    }
}
