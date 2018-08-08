package com.hteos.demo.web;

import com.hteos.biz.user.service.UserService;
import com.hteos.biz.group.service.GroupService;
import com.hteos.biz.installation.dto.Environment;
import com.hteos.biz.installation.dto.WebApp;
import com.hteos.biz.installation.dto.WebGroup;
import com.hteos.biz.installation.entity.Installation;
import com.hteos.biz.installation.service.InstallationService;
import com.hteos.biz.model.UserVo;
import com.hteos.biz.utils.UserContext;
import com.hteos.framework.core.protocol.Result;
import com.hteos.framework.util.Assert;
import com.hteos.framework.web.controller.BaseController;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

@RestController
public class APIController extends BaseController {

    @Autowired
    private InstallationService installationService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    private static String[] WALLPAPERS = new String[]{
            "images/wallpaper/10.jpg",
            "images/wallpaper/11.jpg",
            "images/wallpaper/cloud.jpg",
            "images/wallpaper/fifa.jpg",
            "images/wallpaper/football.jpg",
            "images/wallpaper/wallpaper.jpg"};

    private static String[] THEMES = new String[]{"blue", "green", "purple", "red", "orange", "gray", "cyan"};


    /**
     * 保存壁纸
     *
     * @param wallpaper
     * @return
     */
    @RequestMapping(value = "/api/saveWallpaper")
    public Result<String> saveWallpaper(String wallpaper) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的壁纸");
        this.userService.doSaveWallpaper(wallpaper);
        UserContext.getUser().setWallpaper(wallpaper);
        return this.success();
    }

    /**
     * 保存主题
     *
     * @param theme
     * @return
     */
    @RequestMapping(value = "/api/saveTheme")
    public Result<String> saveTheme(String theme) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的主题");
        this.userService.doSaveTheme(theme);
        UserContext.getUser().setTheme(theme);
        return this.success();
    }

    /**
     * 保存桌面模式
     *
     * @param mode
     * @return
     */
    @RequestMapping(value = "/api/saveMode")
    public Result<String> saveMode(String mode) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的设置");
        this.userService.doSaveMode(mode);
        UserContext.getUser().setMode(mode);
        return this.success();
    }

    /**
     * 保存图标大小
     *
     * @param shortcutSize
     * @return
     */
    @RequestMapping(value = "/api/saveShortcutSize")
    public Result<String> saveShortcutSize(String shortcutSize) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的设置");
        this.userService.doSaveShortcutSize(shortcutSize);
        UserContext.getUser().setShortcutSize(shortcutSize);
        return this.success();
    }

    @RequestMapping("/api/environment")
    public Environment get() {
        Environment environment = this.installationService.getEnvironment(UserContext.getUserId());
        UserVo userVo = UserContext.getUser();
        if (userVo == null) {
            userVo = new UserVo();
            userVo.setMode("metro");
            String wallpaper = WALLPAPERS[(new Random().nextInt(WALLPAPERS.length))];
            String theme = THEMES[new Random().nextInt(THEMES.length)];
            userVo.setLogon(false);
            userVo.setTheme(theme);
            userVo.setWallpaper(wallpaper);
        }
        environment.setPreference(userVo);
        return environment;
    }

    @RequestMapping("/api/app/get")
    public WebApp get(String id) {
        return this.installationService.get(id);
    }

    @RequestMapping("/api/favorite/add")
    public Result<String> addFavorite(String id) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，添加应用收藏失败");
        this.installationService.addFavorite(id);
        return this.success();
    }

    @RequestMapping("/api/favorite/remove")
    public Result<String> deleteFavorite(String id) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，删除应用收藏失败");
        this.installationService.deleteFavorite(id);
        return this.success();
    }

    @RequestMapping("/api/favorites/save")
    public Result<String> saveFavorites(@RequestBody List<Installation> installations) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的设置");
        this.installationService.saveFavorites(installations);
        return this.success();
    }

    @RequestMapping("/api/app/install")
    public Result<String> install(String id) {
        this.installationService.install(id);
        return this.success();
    }

    @RequestMapping("/api/app/check")
    public Result<Boolean> check(String id) {
        return this.success(this.installationService.check(id) != null);
    }

    @RequestMapping("/api/app/uninstall")
    public Result<String> uninstall(String id) throws Exception {
        if (UserContext.isLoggedIn()) {
            this.installationService.uninstall(id);
            return this.success();
        } else {
            return this.failure("您还没有登录，无法卸载应用");
        }
    }

    @RequestMapping("/api/app/hide")
    public Result<String> hide(String id) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的设置");
        this.installationService.hide(id);
        return this.success();
    }

    @RequestMapping("/api/app/show")
    public Result<String> show(String id) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的设置");
        this.installationService.show(id);
        return this.success();
    }

    @RequestMapping("/api/group/save")
    public Result<String> saveGroup(@RequestBody WebGroup webGroup) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的设置");
        this.installationService.saveGroup(webGroup);
        return this.success();
    }

    @RequestMapping("/api/saveShortcut")
    public Result<String> saveShortcutIndex(@RequestBody List<Installation> installations) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的设置");
        this.installationService.saveShortcutIndex(installations);
        return this.success();
    }

    @RequestMapping("/api/group/create")
    public Result<String> createGroup(@RequestBody List<Installation> installations) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法创建分组");
        return this.success("create.success", this.groupService.create(installations));
    }

    @RequestMapping("/api/group/rename")
    public Object renameGroup(String id, String name) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存分组名称");
        this.groupService.rename(id, name);
        return this.success();
    }

    @RequestMapping("/api/group/sort")
    public Object sortGroup(@RequestBody List<WebGroup> groups) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的设置");
        this.groupService.sort(groups);
        return this.success();
    }

    @RequestMapping("/api/group/destroy")
    public Object destroyGroup(String group) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的设置");
        this.groupService.delete(group);
        return this.success();
    }

    @RequestMapping("/api/app/resize")
    public Object resize(String id, String size) {
        Assert.isTrue(UserContext.isLoggedIn(), "您还没有登录，无法保存您的设置");
        this.installationService.resize(id, size);
        return this.success();
    }

    @RequestMapping("/api/proxy")
    public Object proxy(String url) throws IOException {
        return IOUtils.toString(new URL(url),"utf-8");
    }
}
