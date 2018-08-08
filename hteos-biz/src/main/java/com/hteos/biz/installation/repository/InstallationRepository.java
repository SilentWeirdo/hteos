package com.hteos.biz.installation.repository;

import com.hteos.biz.group.entity.Group;
import com.hteos.biz.installation.entity.Installation;
import com.hteos.framework.jpa.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
public interface InstallationRepository extends BaseRepository<Installation> {

    List<Installation> findByUser_Id(String userId);

    Installation getByApp_IdAndUser_Id(String appId, String userId);

    @Query(value =  "select installation,app,agroup,tile,task" +
            " from Installation installation, App app,Group agroup ," +
            "      Tile tile,Task task" +
            "   where installation.app.id = app.id" +
            "     and installation.group.id = agroup.id" +
            "     and app.id = tile.app.id" +
            "     and app.id = task.id" +
            "     and installation.user.id = :userId")
    List<Object[]> listByUserId(@Param("userId") String userId);

    /**
     * 获取分组内最后一个应用
     * @param groupId
     * @return
     */
    Installation getFirstByGroup_IdOrderByIndexDesc(String groupId);
}
