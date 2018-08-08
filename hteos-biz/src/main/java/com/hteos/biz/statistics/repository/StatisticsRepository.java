package com.hteos.biz.statistics.repository;

import com.hteos.biz.statistics.entity.Statistics;
import com.hteos.framework.jpa.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQueries;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author LIQIU
 * @date 2018-6-25
 **/
public interface StatisticsRepository extends BaseRepository<Statistics> {

    /**
     * 根据时间范围查询
     * @param start
     * @param end
     * @return
     */
    List<Statistics> findByDateBetween(Date start, Date end);

    Statistics getByDay(String day);

    @Query("select sum(t.accessCount) as accessCount,sum(t.resigterCount) as resigterCount, sum(t.ipCount) as ipCount" +
            " from Statistics t where t.date between :start and :end")
    Map<String,Object> getStatisticReport(@Param("start") Date start,@Param("end") Date end);

}
