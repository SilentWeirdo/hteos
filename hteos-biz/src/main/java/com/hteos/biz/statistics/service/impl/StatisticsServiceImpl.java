package com.hteos.biz.statistics.service.impl;

import com.hteos.biz.access.repository.AccessRepository;
import com.hteos.biz.access.entity.Access;
import com.hteos.biz.statistics.entity.Statistics;
import com.hteos.biz.statistics.repository.StatisticsRepository;
import com.hteos.biz.statistics.service.StatisticsService;
import com.hteos.framework.support.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Override
    public void doStatistics(int type) {
        Date date = new Date();
        String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Statistics statistics = this.statisticsRepository.getByDay(day);
        if (statistics == null) {
            statistics = new Statistics();
            statistics.setDay(day);
            statistics.setDate(date);
        }
        // 1 新的独立访问
        if (type == 1) {
            statistics.setAccessCount(statistics.getAccessCount() + 1);
            statistics.setIpCount(statistics.getIpCount() + 1);
            // 流量访问
        } else if (type == 2) {
            statistics.setAccessCount(statistics.getAccessCount() + 1);
            // 用户注册
        } else if (type == 3) {
            statistics.setResigterCount(statistics.getResigterCount() + 1);
        }
        this.statisticsRepository.save(statistics);
    }

    @Override
    public Map<String, Object> get(Date start, Date end) {
        /*DetachedCriteria dc = DetachedCriteria.forClass(Statistics.class);
        dc.add(Restrictions.ge("date", start));
        dc.add(Restrictions.le("date", end));
        dc.setProjection(Projections.sum("accessCount"));
        Map<String, Object> result = new HashMap<>();
        result.put("accessCount", dc.getExecutableCriteria(this.commonRepository.getSession()).uniqueResult());
        dc.setProjection(Projections.sum("resigterCount"));
        result.put("resigterCount", dc.getExecutableCriteria(this.commonRepository.getSession()).uniqueResult());
        dc.setProjection(Projections.sum("ipCount"));
        result.put("ipCount", dc.getExecutableCriteria(this.commonRepository.getSession()).uniqueResult());*/
        return this.statisticsRepository.getStatisticReport(start, end);
    }

    @Override
    public Page<Access> list(Date start, Date end, Integer size, Integer page) {
        /*DetachedCriteria dc = DetachedCriteria.forClass(Access.class);
        PageRequest.of(page, size);
        dc.add(Restrictions.between("date", start, end));
        return this.commonRepository.findByPage(dc, size, page);*/
        return this.accessRepository.findByDateBetween(start, end, PageRequest.of(page, size));
    }

    @Override
    public Object getMapData(Date start, Date end) {
       /* DetachedCriteria dc = DetachedCriteria.forClass(Access.class);
        dc.add(Restrictions.between("date", start, end));
        ProjectionList list = Projections.projectionList();
        list.add(Projections.count("id").as("access"));
        list.add(Projections.sum("accessCount").as("views"));
        list.add(Projections.groupProperty("province").as("province"));
        dc.setProjection(list);
        dc.createAlias("", "access");
        dc.setResultTransformer(DetachedCriteria.ALIAS_TO_ENTITY_MAP);*/
        //dc.getExecutableCriteria(this.commonRepository.getSession()).list()
        return this.accessRepository.groupByProvice(start, end);
    }

    @Override
    public Object getDeviceData(Date start, Date end) {
       /* DetachedCriteria dc = DetachedCriteria.forClass(Access.class);
        dc.add(Restrictions.between("date", start, end));
        dc.add(Restrictions.isNotNull("device"));
        ProjectionList list = Projections.projectionList();
        list.add(Projections.count("id").as("value"));
        list.add(Projections.groupProperty("device").as("name"));
        dc.setProjection(list);
        dc.createAlias("", "device");
        dc.setResultTransformer(DetachedCriteria.ALIAS_TO_ENTITY_MAP);
        return dc.getExecutableCriteria(this.commonRepository.getSession()).list();*/
        return this.accessRepository.groupByDevice(start, end);
    }

    @Override
    public Object getOSData(Date start, Date end) {
      /*  DetachedCriteria dc = DetachedCriteria.forClass(Access.class);
        dc.add(Restrictions.between("date", start, end));
        dc.add(Restrictions.isNotNull("os"));
        ProjectionList list = Projections.projectionList();
        list.add(Projections.count("id").as("value"));
        list.add(Projections.groupProperty("os").as("name"));
        dc.setProjection(list);
        dc.createAlias("", "os");
        dc.setResultTransformer(DetachedCriteria.ALIAS_TO_ENTITY_MAP);
        return dc.getExecutableCriteria(this.commonRepository.getSession()).list();*/
        return this.accessRepository.groupByOS(start, end);
    }

    @Override
    public Object getBrowserData(Date start, Date end) {
       /* DetachedCriteria dc = DetachedCriteria.forClass(Access.class);
        dc.add(Restrictions.between("date", start, end));
        dc.add(Restrictions.isNotNull("browser"));
        ProjectionList list = Projections.projectionList();
        list.add(Projections.count("id").as("value"));
        list.add(Projections.groupProperty("browser").as("name"));
        dc.setProjection(list);
        dc.createAlias("", "browser");
        dc.setResultTransformer(DetachedCriteria.ALIAS_TO_ENTITY_MAP);
        return dc.getExecutableCriteria(this.commonRepository.getSession()).list();*/
        return this.accessRepository.groupByBrowser(start, end);
    }

    @Override
    public Object getBrowserVersionData(Date start, Date end, String browser) {
        /*DetachedCriteria dc = DetachedCriteria.forClass(Access.class);
        dc.add(Restrictions.between("date", start, end));
        dc.add(Restrictions.eq("browser", browser));
        ProjectionList list = Projections.projectionList();
        list.add(Projections.count("id").as("value"));
        list.add(Projections.groupProperty("browserVersion").as("name"));
        dc.setProjection(list);
        dc.createAlias("", "browser");
        dc.setResultTransformer(DetachedCriteria.ALIAS_TO_ENTITY_MAP);
        return dc.getExecutableCriteria(this.commonRepository.getSession()).list();*/
        return this.accessRepository.groupByBrowserVersion(start, end, browser);
    }
}
