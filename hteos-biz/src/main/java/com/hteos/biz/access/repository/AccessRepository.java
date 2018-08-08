package com.hteos.biz.access.repository;

import com.hteos.biz.access.entity.Access;
import com.hteos.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-6-29
 **/
public interface AccessRepository extends BaseRepository<Access> {

    Access getByIpAndDay(String ip,String day);

    Page<Access> findByDateBetween(Date start, Date end, Pageable pageable);

    @Query("select count(t.id) as access ,sum(t.accessCount) as views,province  " +
            " from Access t where t.date between :start and :end " +
            " group by province")
    List<Map<String,Object>> groupByProvice(@Param("start") Date start, @Param("end") Date end);

    @Query("select count(t.id) as value, device as name  " +
            " from Access t where t.date between :start and :end " +
            " group by device")
    List<Map<String,Object>> groupByDevice(@Param("start") Date start, @Param("end") Date end);

    @Query("select count(t.id) as value, os as name  " +
            " from Access t where t.date between :start and :end " +
            " group by os")
    List<Map<String,Object>> groupByOS(@Param("start") Date start, @Param("end") Date end);

    @Query("select count(t.id) as value, browser as name  " +
            " from Access t where t.date between :start and :end " +
            " group by browser")
    List<Map<String,Object>> groupByBrowser(@Param("start") Date start, @Param("end") Date end);

    @Query("select count(t.id) as value, browserVersion as name  " +
            " from Access t where t.date between :start and :end " +
            "  and t.browserVersion = :browserVersion " +
            " group by browserVersion")
    List<Map<String,Object>> groupByBrowserVersion(@Param("start") Date start, @Param("end") Date end,@Param("browserVersion") String browserVersion);
}
