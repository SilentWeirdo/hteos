package com.hteos.biz.group.service;

import com.hteos.biz.group.entity.Group;
import com.hteos.biz.installation.dto.WebGroup;
import com.hteos.biz.installation.entity.Installation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
public interface GroupService {

    @Transactional(readOnly = true)
    Group getMaxGroup(String accountId);

    @Transactional(readOnly = true)
    List<Group> getGroups(String accountId);
    @Transactional
    String create(List<Installation> installations);

    @Transactional
    void sort(List<WebGroup> groups);

    @Transactional
    void rename(String id, String name);

    @Transactional
    void delete(String group);
}
