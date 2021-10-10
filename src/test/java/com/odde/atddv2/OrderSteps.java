package com.odde.atddv2;

import com.odde.atddv2.page.OrderPage;
import com.odde.atddv2.page.WelcomePage;
import io.cucumber.datatable.DataTable;
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
    private OrderPage orderPage;

    @SneakyThrows
    @那么("显示如下订单")
    public void 显示如下订单(DataTable table) {
        table.asList().forEach(browser::shouldHaveText);
    }

    @当("用如下数据录入订单:")
    public void 用如下数据录入订单(DataTable table) {
        welcomePage.goToOrders();
        orderPage.addOrder(table.asMaps().get(0));
    }
}
