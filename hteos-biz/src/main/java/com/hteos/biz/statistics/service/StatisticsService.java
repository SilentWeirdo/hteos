package com.hteos.biz.statistics.service;

import java.util.Date;
import java.util.Map;

import com.hteos.biz.access.entity.Access;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface StatisticsService {

    @Transactional
    void doStatistics(int type);

    @Transactional(readOnly = true)
    Map<String, Object> get(Date start, Date end);

    @Transactional(readOnly = true)
    Page<Access> list(Date start, Date end, Integer size, Integer page);

    @Transactional(readOnly = true)
    Object getMapData(Date start, Date end);

    @Transactional(readOnly = true)
    Object getDeviceData(Date start, Date end);

    @Transactional(readOnly = true)
    Object getOSData(Date start, Date end);

    @Transactional(readOnly = true)
    Object getBrowserData(Date start, Date end);

    @Transactional(readOnly = true)
    Object getBrowserVersionData(Date start, Date end, String browser);
}
