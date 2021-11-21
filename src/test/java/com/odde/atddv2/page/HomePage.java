package com.odde.atddv2.page;

import com.odde.atddv2.WinForm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomePage {

    @Autowired
    public WinForm winForm;

    @SneakyThrows
    public void login(String userName, String password) {
        winForm.inputTextById("textBox_userName", userName);
        winForm.inputTextById("textBox_password", password);
        winForm.clickById("userButton_login");
        System.out.println(winForm.getWindowsDriver().getWindowHandles());
    }

    public void shouldHaveMessage(String message) {
        winForm.shouldHaveText(message);
    }
}
