package com.hteos.biz.installation.service.impl;

import com.hteos.biz.user.entity.User;
import com.hteos.biz.user.repository.UserRepository;
import com.hteos.biz.app.entity.App;
import com.hteos.biz.app.entity.Task;
import com.hteos.biz.app.entity.Tile;
import com.hteos.biz.app.repository.AppRepository;
import com.hteos.biz.group.entity.Group;
import com.hteos.biz.group.repository.GroupRepository;
import com.hteos.biz.installation.dto.*;
import com.hteos.biz.installation.entity.Installation;
import com.hteos.biz.installation.repository.InstallationRepository;
import com.hteos.biz.installation.service.InstallationService;
import com.hteos.biz.utils.UserContext;
import com.hteos.framework.util.Assert;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
@Service
public class InstallationServiceImpl implements InstallationService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private InstallationRepository installationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppRepository appRepository;

    /**
     * 模板账户邮箱
     */
    @Value("${hteos.template-user:}")
    private String templateAccount;

    @Override
    public Environment getEnvironment(String userId) {

        Environment environment = new Environment();

        if (userId == null) {
            User user = userRepository.getByEmail(templateAccount);
            if (user != null) {
                userId = user.getId();
            }
        }

        String hql = "select installation,app,agroup,tile,task" +
                " from Installation installation, App app,Group agroup ," +
                "      Tile tile,Task task" +
                "   where installation.app.id = app.id" +
                "     and installation.group.id = agroup.id" +
                "     and app.id = tile.app.id" +
                "     and app.id = task.id" +
                "     and installation.user.id = ?";

        List<Object[]> list = this.installationRepository.listByUserId(userId);

        Map<String, WebGroup> groupMap = new HashMap<>();
        List<Favorite> favorites = new ArrayList<>();

        list.forEach(objects -> {
            Installation installation = (Installation) objects[0];

            App app = (App) objects[1];
            app.setTile((Tile) objects[3]);
            app.setTask((Task) objects[4]);
            installation.setApp(app);
            installation.setGroup((Group) objects[2]);

            Group group = installation.getGroup();
            WebGroup webGroup = groupMap.get(group.getId());
            if (webGroup == null) {
                webGroup = new WebGroup();
                BeanUtils.copyProperties(group, webGroup);
                groupMap.put(group.getId(), webGroup);
            }

            if (installation.getFavorited() != null && installation.getFavorited()) {
                Favorite favorite = new Favorite();
                favorite.setId(installation.getId());
                favorite.setIndex(installation.getFavoriteIndex());
                favorites.add(favorite);
            }

            webGroup.getApps().add(convert(installation));

        });

        environment.setGroups(groupMap.values());
        environment.setFavorites(favorites);

        return environment;
    }

    @Override
    public WebApp get(String id) {
        Installation installation = this.installationRepository.getByApp_IdAndUser_Id(id, UserContext.getUser().getId());
        if (installation != null) {
            return this.convert(installation);
        }
        return null;
    }

    private WebApp convert(Installation installation) {
        WebApp webApp = new WebApp();
        BeanUtils.copyProperties(installation.getApp(), webApp);
        BeanUtils.copyProperties(installation, webApp, "group");

        WebAppTile webAppTile = new WebAppTile();
        BeanUtils.copyProperties(installation.getApp().getTile(), webAppTile);
        webApp.setTile(webAppTile);

        WebAppTask webAppTask = new WebAppTask();
        BeanUtils.copyProperties(installation.getApp().getTask(), webAppTask);
        webApp.setTask(webAppTask);

        webApp.setGroup(installation.getGroup().getId());

        if (StringUtils.isEmpty(installation.getSize())) {
            webApp.getTile().setSize(installation.getApp().getTile().getSize());
        } else {
            webApp.getTile().setSize(installation.getSize());
        }


        if (StringUtils.isEmpty(installation.getName())) {
            webApp.setName(installation.getApp().getName());
        }
        return webApp;
    }


    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void init(User user) {
        User adminUser = userRepository.getByEmail(templateAccount);
        List<Group> groups = groupRepository.findByUser_Id(adminUser.getId());
        Map<String, Group> groupMapping = new HashMap<String, Group>();
        //先复制分组
        for (Group group : groups) {
            Group newGroup = new Group();
            newGroup.setUser(user);
            newGroup.setName(group.getName());
            newGroup.setIndex(group.getIndex());
            this.groupRepository.save(newGroup);
            groupMapping.put(group.getId(), newGroup);
        }
        //复制应用
        List<Installation> installations = this.installationRepository.findByUser_Id(adminUser.getId());
        for (Installation installation : installations) {
            Installation app = new Installation();
            app.setApp(installation.getApp());
            app.setUser(user);
            app.setInstallDate(new Date());
            app.setGroup(groupMapping.get(installation.getGroup().getId()));
            app.setIndex(installation.getIndex());
            app.setSize(installation.getSize());
            app.setFavoriteIndex(installation.getFavoriteIndex());
            app.setFavorited(installation.getFavorited());
            this.installationRepository.save(app);
        }
    }

    @Override
    public Installation check(String id) {
        return this.installationRepository.getByApp_IdAndUser_Id(id, UserContext.getUser().getId());
    }


    /**
     * 安装应用
     *
     * @param id
     */
    @Override
    public WebApp install(String id) {

        Assert.isTrue(UserContext.isLoggedIn(),"没有登录");

        User user = userRepository.findById(UserContext.getUser().getId()).get();
        App app = appRepository.findById(id).get();

        Installation installation = new Installation();
        Group group = this.groupRepository.getFirstByUser_IdOrderByIndexDesc(user.getId());
        installation.setUser(user);
        installation.setApp(app);
        installation.setInstallDate(new Date());

        Installation lastInstallation = this.installationRepository.getFirstByGroup_IdOrderByIndexDesc(group.getId());
        Integer index = lastInstallation != null ? lastInstallation.getIndex() + 1 : 1;
        installation.setIndex(index);
        installation.setGroup(group);
        this.installationRepository.save(installation);

        Integer installCount = app.getInstallCount();
        installCount = installCount == null ? 0 : installCount;
        app.setInstallCount(installCount + 1);
        this.appRepository.save(app);

        return this.convert(installation);
    }

    @Override
    public void uninstall(String id) throws Exception {
        Installation installation = this.installationRepository.getOne(id);
        App app = installation.getApp();
        if (app.getIsNative() != null) {
            Assert.isTrue(!app.getIsNative(), "无法卸载系统应用");
        }
        this.installationRepository.delete(installation);
    }

    @Override
    public void saveGroup(WebGroup webGroup) {
        Group group = this.groupRepository.getOne(webGroup.getId());
        webGroup.getApps().forEach(webApp -> {
            Installation installation = this.installationRepository.getOne(webApp.getId());
            installation.setGroup(group);
            installation.setIndex(webApp.getIndex());
            this.installationRepository.save(installation);
        });
    }


    @Override
    public void addFavorite(String id) {
        Installation installation = this.installationRepository.getOne(id);
        installation.setFavorited(true);
        this.installationRepository.save(installation);
    }

    @Override
    public void deleteFavorite(String id) {
        Installation installation = this.installationRepository.getOne(id);
        installation.setFavorited(false);
        installation.setFavoriteIndex(null);
        this.installationRepository.save(installation);
    }

    @Override
    public void saveFavorites(List<Installation> installations) {
        for (Installation installation : installations) {
            Installation installation2 = this.installationRepository.getOne(installation.getId());
            installation2.setFavorited(true);
            installation2.setFavoriteIndex(installation.getIndex());
            this.installationRepository.save(installation2);
        }
    }


    @Override
    public void saveShortcutIndex(List<Installation> installations) {
        for (Installation installation : installations) {
            Installation installation2 = this.installationRepository.getOne(installation.getId());
            installation2.setShortcutIndex(installation.getIndex());
            this.installationRepository.save(installation2);
        }
    }

    @Override
    public void hide(String id) {
        Installation installation = this.installationRepository.getOne(id);
        installation.setHidden(true);
        this.installationRepository.save(installation);
    }

    @Override
    public void show(String id) {
        Installation installation = this.installationRepository.getOne(id);
        installation.setHidden(false);
        this.installationRepository.save(installation);
    }


    @Override
    public void resize(String id, String size) {
        Installation installation = this.installationRepository.getOne(id);
        installation.setSize(size);
        this.installationRepository.save(installation);
    }
}
