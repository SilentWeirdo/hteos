package com.hteos.biz.group.repository;

import com.hteos.biz.group.entity.Group;
import com.hteos.framework.jpa.repository.BaseRepository;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
public interface GroupRepository extends BaseRepository<Group> {

    /**
     * 获取最后一个分组
     * @param accountId
     * @return
     */
    Group getFirstByUser_IdOrderByIndexDesc(String accountId);

    /**
     * 根据账号获得分组
     * @param accountId
     * @return
     */
    List<Group> findByUser_Id(String accountId);
}
