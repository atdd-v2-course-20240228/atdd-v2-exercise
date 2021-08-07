package com.odde.atddv2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.atddv2.entity.Order;
import com.odde.atddv2.entity.OrderLine;
import com.odde.atddv2.entity.User;
import com.odde.atddv2.repo.OrderRepo;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.zh_cn.并且;
import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_cn.那么;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class ApiOrderSteps {

    private OkHttpClient okHttpClient = new OkHttpClient();

    private String response;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private LoginSteps loginSteps;

    @SneakyThrows
    @当("API查询订单时")
    public void api查询订单时() {
        loginSteps.存在用户名为和密码为的用户("j", "j");
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(new User().setUserName("j").setPassword("j")));
        Request request = new Request.Builder().url("http://localhost:10081/users/login").post(requestBody).build();
        String token = okHttpClient.newCall(request).execute().header("token");

        request = new Request.Builder()
                .url("http://localhost:10081/api/orders")
                .header("Accept", "application/json")
                .header("token", token)
                .get().build();

        response = okHttpClient.newCall(request).execute().body().string();
    }

    @并且("存在订单{string}的订单项:")
    @Transactional
    public void 存在订单的订单项(String orderCode, DataTable table) {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = orderRepo.findByCode(orderCode);
        table.asMaps().forEach(map -> order.getLines().add(objectMapper.convertValue(map, OrderLine.class).setOrder(order)));
        orderRepo.save(order);
    }

    @SneakyThrows
    @那么("返回如下订单")
    public void 返回如下订单(String json) {
        JSONAssert.assertEquals(json, response, false);
    }
}
