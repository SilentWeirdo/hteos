package com.hteos.demo.register.service.impl;

import com.hteos.biz.access.service.AccessService;
import com.hteos.biz.user.entity.User;
import com.hteos.biz.user.repository.UserRepository;
import com.hteos.biz.installation.service.InstallationService;
import com.hteos.biz.statistics.service.StatisticsService;
import com.hteos.demo.login.service.LoginService;
import com.hteos.framework.util.Assert;
import com.hteos.framework.util.WebUtils;
import com.hteos.demo.register.service.RegisterService;
import com.hteos.framework.bean.Position;
import com.hteos.framework.exception.account.AlreadyExistsException;
import com.hteos.framework.util.EncryptUtils;
import com.hteos.framework.util.IPUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public StatisticsService statisticsService;

    @Autowired
    private InstallationService installationService;

    @Autowired
    private AccessService accessService;

    @Autowired
    private LoginService loginService;

    @Override
    public int checkAccount(String account) {
        return userRepository.findByUsernameOrEmail(account, account).size();
    }

    @Override
    public void register(User user) {
        Assert.isTrue(this.checkAccount(user.getUsername()) <= 0, "账号已存在");
        user.setEmail(user.getUsername());
        user.setPassword(EncryptUtils.getMD5(user.getPassword()));
        user.setFace("images/face.png");
        user.setWallpaper("images/wallpaper/wallpaper.jpg");
        user.setNickName(StringUtils.capitalize(user.getUsername().substring(0, user.getUsername().indexOf("@"))));
        user.setTheme("blue");
        user.setCreateDate(new Date());
        this.doRegister(user);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void doRegister(User user) {
        user = this.userRepository.save(user);
        this.installationService.init(user);
        this.accessService.setUser(user.getNickName());
        this.statisticsService.doStatistics(3);
        this.loginService.doLogon(user);
    }


    @Override
    public void registerByQQ(User user) {
        User user2 = this.userRepository.getByQqOpenId(user.getQqOpenId());
        if (user2 != null) {
            this.loginService.doLogon(user2);
        } else {
            String id = String.valueOf(System.currentTimeMillis());
            user.setEmail("qq" + id + "@qq.com");
            user.setUsername("qq" + id + "@qq.com");
            user.setPassword(EncryptUtils.getMD5("123456"));
            user.setWallpaper("images/wallpaper/wallpaper.jpg");
            user.setTheme("blue");
            user.setCreateDate(new Date());
            this.doRegister(user);
        }
    }
}
