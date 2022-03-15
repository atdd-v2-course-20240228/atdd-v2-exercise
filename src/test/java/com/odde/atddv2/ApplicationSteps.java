package com.odde.atddv2;

import com.odde.atddv2.repo.OrderRepo;
import com.odde.atddv2.repo.UserRepo;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {CucumberConfiguration.class}, loader = SpringBootContextLoader.class)
@CucumberContextConfiguration
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

}
