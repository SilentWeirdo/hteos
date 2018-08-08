package com.hteos.demo.login.service.impl;

import com.hteos.biz.user.entity.User;
import com.hteos.biz.user.repository.UserRepository;
import com.hteos.biz.model.UserVo;
import com.hteos.biz.utils.UserContext;
import com.hteos.demo.cache.UserCacheService;
import com.hteos.demo.login.service.LoginService;
import com.hteos.demo.register.service.RegisterService;
import com.hteos.framework.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserVo doLogin(String username, String password) {
        User user = this.userRepository.getByUsernameOrEmail(username, username);
        Assert.isTrue(user != null && user.getPassword().equals(password), "账号或密码错误");
        return this.doLogon(user);
    }

    @Override
    public UserVo doQQLogin(String openId) {
        User user = userRepository.getByQqOpenId(openId);
        Assert.isTrue(user != null, "用户不存在");
        return this.doLogon(user);
    }

    @Override
    public UserVo doLogon(User user) {
        UserVo userVo = new UserVo();
        userVo.setLogon(true);
        BeanUtils.copyProperties(user, userVo);
        UserContext.put(userVo);
        return userVo;
    }
}
