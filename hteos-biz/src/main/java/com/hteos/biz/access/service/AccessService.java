package com.hteos.biz.access.service;

import com.hteos.biz.access.entity.Access;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LIQIU
 * @date 2018-6-29
 **/
public interface AccessService {

    @Transactional
    void setUser(String username);

    @Transactional
    public void save(Access access);

    @Transactional
    void setDuration(String ip, Long duration);
}
