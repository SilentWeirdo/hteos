package com.hteos.admin.web;

import com.hteos.biz.app.entity.App;
import com.hteos.biz.app.service.AppService;
import com.hteos.biz.installation.dto.AppDto;
import com.hteos.biz.installation.dto.WebApp;
import com.hteos.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AppController extends BaseController {

    @Autowired
    private AppService appService;

    @RequestMapping("/app/save")
    public Object save(AppDto appDto) {
        String id = appService.save(appDto);
        System.out.println("app id ============> " + id);
        return this.success(id);
    }

    @RequestMapping("/app/delete")
    public Object delete(String ids) {
        appService.delete(ids);
        return this.success();
    }

    @RequestMapping("/app/upload")
    public Object upload(MultipartFile icon, MultipartFile image, String id) {
        this.appService.doUpload(id, icon, image);
        return this.success();
    }

    @RequestMapping("/app/get")
    public Object get(String id) {
        return this.appService.getDto(id);
    }

    @RequestMapping("/app/search")
    public Object search(String condition, Integer size, Integer page) {
        return appService.search(condition, size, page);
    }
}
