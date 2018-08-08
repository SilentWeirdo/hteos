package com.hteos.demo.login.service;

import com.hteos.biz.model.UserVo;
import com.hteos.biz.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface LoginService {

    @Transactional
    UserVo doLogin(String account, String password);

    @Transactional
    UserVo doQQLogin(String openId);

    UserVo doLogon(User user);
}
