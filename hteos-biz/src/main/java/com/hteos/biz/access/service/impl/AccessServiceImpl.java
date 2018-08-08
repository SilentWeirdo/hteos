package com.hteos.biz.access.service.impl;

import com.hteos.biz.access.entity.Access;
import com.hteos.biz.access.repository.AccessRepository;
import com.hteos.biz.access.service.AccessService;
import com.hteos.biz.statistics.service.StatisticsService;
import com.hteos.framework.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LIQIU
 * @date 2018-6-29
 **/
@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private StatisticsService statisticsService;

    @Override
    public void setUser(String username) {
        Access access = this.accessRepository.getByIpAndDay(WebUtils.getClientIp(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        if (access != null) {
            access.setUser(username);
            this.accessRepository.save(access);
        }
    }

    @Override
    public void save(Access access) {
        Access accessOld = accessRepository.getByIpAndDay(access.getIp(), access.getDay());
        if (accessOld == null) {
            // 独立访问
            this.accessRepository.save(access);
            statisticsService.doStatistics(1);
        } else {
            if (accessOld.getAccessCount() == null) {
                accessOld.setAccessCount(1L);
            }
            accessOld.setAccessCount(accessOld.getAccessCount() + 1);
            this.accessRepository.save(accessOld);
            statisticsService.doStatistics(2);
        }
    }

    @Override
    public void setDuration(String ip, Long duration){
        Access access = this.accessRepository.getByIpAndDay(ip,new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        if(access != null){
            access.setDuration(duration);
        }
        this.accessRepository.save(access);
    }
}
