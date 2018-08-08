package com.hteos.demo.register.controller;

import com.hteos.biz.user.entity.User;
import com.hteos.demo.register.service.RegisterService;
import com.hteos.framework.exception.account.AlreadyExistsException;
import com.hteos.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController extends BaseController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/register")
    public Object register(User user) {
        this.registerService.register(user);
        return this.success();
    }

    @RequestMapping("/registerByQQ")
    public Object registerByQQ(User user) {
        this.registerService.registerByQQ(user);
        return this.success();
    }


    @RequestMapping("/register/check")
    public Object checkAccount(String account) {
        return this.registerService.checkAccount(account);
    }

}
