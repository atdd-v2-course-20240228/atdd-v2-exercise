package com.odde.atddv2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.atddv2.entity.Order;
import com.odde.atddv2.page.OrderPage;
import com.odde.atddv2.page.WelcomePage;
import com.odde.atddv2.repo.OrderRepo;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.zh_cn.假如;
import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_cn.那么;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderSteps {

    @Autowired
    private WelcomePage welcomePage;

    @Autowired
    private Browser browser;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderPage orderPage;

    @假如("存在如下订单:")
    public void 存在如下订单(DataTable table) {
        ObjectMapper objectMapper = new ObjectMapper();
        table.asMaps().forEach(map -> orderRepo.save(objectMapper.convertValue(map, Order.class)));
    }

    @当("查询订单时")
    public void 查询订单时() {
        welcomePage.goToOrders();
    }

    @SneakyThrows
    @那么("显示如下订单")
    public void 显示如下订单(DataTable table) {
        table.asList().forEach(browser::shouldHaveText);
    }

    @当("用如下数据录入订单:")
    public void 用如下数据录入订单(DataTable table) {
        查询订单时();
        orderPage.addOrder(table.asMaps().get(0));
    }
}
