package com.hteos.demo.login.controller;

import com.hteos.biz.model.UserVo;
import com.hteos.demo.login.service.LoginService;
import com.hteos.framework.core.protocol.Result;
import com.hteos.framework.util.Assert;
import com.hteos.framework.util.EncryptUtils;
import com.hteos.framework.util.WebUtils;
import com.hteos.framework.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public Result<UserVo> login(String username, String password) {
        Assert.isTrue(StringUtils.isNotEmpty(username), "账号不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(password), "密码不能为空");
        UserVo userVo = this.loginService.doLogin(username, EncryptUtils.getMD5(password));
        return this.success(userVo);
    }

    @RequestMapping(value = "/loginByQQ")
    public Result<String> login(String openId) {
        if (StringUtils.isNotEmpty(openId)) {
            this.loginService.doQQLogin(openId);
            return this.success();
        } else {
            return this.failure("帐号为空");
        }
    }


    @RequestMapping(value = "/logout")
    public Result<String> logout() {
        WebUtils.getSession().invalidate();
        return this.success();
    }
}
