package com.hteos.biz.group.service.impl;

import com.hteos.biz.user.entity.User;
import com.hteos.biz.user.repository.UserRepository;
import com.hteos.biz.group.entity.Group;
import com.hteos.biz.group.repository.GroupRepository;
import com.hteos.biz.group.service.GroupService;
import com.hteos.biz.installation.dto.WebGroup;
import com.hteos.biz.installation.entity.Installation;
import com.hteos.biz.installation.repository.InstallationRepository;
import com.hteos.biz.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstallationRepository installationRepository;

    @Override
    public Group getMaxGroup(String accountId) {
        return this.groupRepository.getFirstByUser_IdOrderByIndexDesc(accountId);
    }

    @Override
    public List<Group> getGroups(String accountId) {
        return this.groupRepository.findByUser_Id(accountId);
    }


    @Override
    public String create(List<Installation> installations) {
        User user = userRepository.findById(UserContext.getUserId()).get();
        Group lastGroup = this.groupRepository.getFirstByUser_IdOrderByIndexDesc(user.getId());
        Integer index = lastGroup != null ? lastGroup.getIndex() + 1 : 1;
        Group group = new Group();
        group.setUser(user);
        group.setName("新建分组");
        group.setIndex(index);
        Group newGroup = this.groupRepository.save(group);

        if(installations != null){
            installations.forEach(installation -> {
                Installation installation1 = this.installationRepository.getOne(installation.getId());
                installation1.setGroup(newGroup);
                installation1.setIndex(installation.getIndex());
                this.installationRepository.save(installation1);
            });
        }

        return newGroup.getId();
    }

    @Override
    public void sort(List<WebGroup> groups) {
        groups.forEach(group ->{
            Group group1 = this.groupRepository.getOne(group.getId());
            group1.setIndex(group.getIndex());
            this.groupRepository.save(group1);
        });
    }

    @Override
    public void rename(String id, String name) {
        Group group = this.groupRepository.getOne(id);
        group.setName(name);
        this.groupRepository.save(group);
    }


    @Override
    public void delete(String id) {
        this.groupRepository.deleteById(id);
    }

}
