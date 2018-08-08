package com.hteos.demo.register.service;

import com.hteos.biz.user.entity.User;
import com.hteos.framework.exception.account.AlreadyExistsException;
import org.springframework.transaction.annotation.Transactional;


public interface RegisterService {

    @Transactional(readOnly = true)
    int checkAccount(String account);

    @Transactional
    void register(User user);

    @Transactional
    void registerByQQ(User user);
}
