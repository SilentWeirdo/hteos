package com.hteos.biz.app.service.impl;

import com.hteos.biz.app.entity.App;
import com.hteos.biz.app.entity.Task;
import com.hteos.biz.app.entity.Tile;
import com.hteos.biz.app.repository.AppRepository;
import com.hteos.biz.app.service.AppService;
import com.hteos.biz.app.storage.AliyunOssStorage;
import com.hteos.biz.installation.dto.AppDto;
import com.hteos.biz.installation.dto.WebAppTask;
import com.hteos.biz.installation.dto.WebAppTile;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AliyunOssStorage aliyunOssStorage;

    @Autowired
    private AppRepository appRepository;

    @Value("${hteos.image-server}")
    private String imageServer;

    @Override
    public String save(AppDto appDto) {
        App app;
        if (StringUtils.isEmpty(appDto.getId())) {
            app = new App();
            app.setProvider("HteOS官方");
            app.setCreateDate(new Date());
            app.setScore(0);
            app.setInstallCount(0);
            app.setReviewCount(0);
            BeanUtils.copyProperties(appDto, app, "tile", "task");
            Tile tile = new Tile();
            BeanUtils.copyProperties(appDto.getTile(), tile);
            Task task = new Task();
            BeanUtils.copyProperties(appDto.getTask(), task);
            app.setTile(tile);
            app.setTask(task);
            app.setReleaseDate(new Date());
            app = appRepository.save(app);
            return app.getId();
        } else {
            app = this.appRepository.findById(appDto.getId()).get();
            BeanUtils.copyProperties(appDto, app, "tile", "task");
            BeanUtils.copyProperties(appDto.getTile(), app.getTile());
            BeanUtils.copyProperties(appDto.getTask(), app.getTask());
            appRepository.save(app);
            return app.getId();
        }
    }

    @Override
    public void delete(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            this.appRepository.delete(this.get(id));
        }
    }

    @Override
    public Page<AppDto> search(String condition, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<App> result;
        if (StringUtils.isNotEmpty(condition)) {
            result = this.appRepository.findByNameContains(condition, pageable);
        } else {
            result = this.appRepository.findAll(pageable);
        }

        return result.map(app -> {
            AppDto appDto = new AppDto();
            Task task = app.getTask();
            Tile tile = app.getTile();
            BeanUtils.copyProperties(app, appDto);

            WebAppTile webAppTile = new WebAppTile();
            BeanUtils.copyProperties(tile,webAppTile);

            WebAppTask webAppTask = new WebAppTask();
            BeanUtils.copyProperties(task,webAppTask);

            appDto.setTile(webAppTile);
            appDto.setTask(webAppTask);
            return appDto;
        });
    }

    @Override
    public App get(String id) {
        return this.appRepository.findById(id).get();
    }

    @Override
    public AppDto getDto(String id) {
        App app = this.appRepository.findById(id).get();
        AppDto appDto = new AppDto();
        Task task = app.getTask();
        Tile tile = app.getTile();
        BeanUtils.copyProperties(app, appDto);

        WebAppTile webAppTile = new WebAppTile();
        BeanUtils.copyProperties(tile,webAppTile);

        WebAppTask webAppTask = new WebAppTask();
        BeanUtils.copyProperties(task,webAppTask);

        appDto.setTile(webAppTile);
        appDto.setTask(webAppTask);
        return appDto;
    }

    @Override
    public void doUpload(String id, MultipartFile icon, MultipartFile image) {
        try {

            App app = this.get(id);
            if (icon != null && !icon.isEmpty()) {
                String path = "images/groups/icons/" + id + ".png";
                aliyunOssStorage.upload(icon.getInputStream(), path);
                app.setIcon(imageServer + "/" + path);
            }

            if (image != null && !image.isEmpty()) {
                String path = "images/groups/images/" + id + ".png";
                aliyunOssStorage.upload(image.getInputStream(), path);
                app.setImage(imageServer + "/" + path);
            }

            if (image != null || icon != null) {
                this.appRepository.save(app);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
