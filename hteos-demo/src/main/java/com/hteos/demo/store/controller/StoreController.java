package com.hteos.demo.store.controller;

import java.util.HashMap;
import java.util.Map;

import com.hteos.biz.installation.dto.WebApp;
import com.hteos.biz.installation.service.InstallationService;
import com.hteos.biz.review.service.ReviewService;
import com.hteos.framework.core.protocol.Result;
import com.hteos.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hteos.demo.store.service.StoreService;

/**
 * @author LIQIU
 */
@Controller
public class StoreController extends BaseController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private InstallationService installationService;

    @Autowired
    private ReviewService reviewService;

    @ResponseBody
    @RequestMapping("/store/channel/{channel}")
    public Object getChannel(@PathVariable String channel, Integer size) {
        if (channel.equals("hot")) {
            return storeService.getHotChannel(size);
        } else if (channel.equals("install")) {
            return storeService.getInstallChannel(size);
        } else if (channel.equals("score")) {
            return storeService.getScoreChannel(size);
        } else if (channel.equals("new")) {
            return storeService.getNewChannel(size);
        }
        return null;
    }

    @RequestMapping("/store/category/{category}")
    public @ResponseBody
    Object getCategory(@PathVariable String category, Integer size, Integer page) {
        return this.storeService.getCategory(category, size, page);
    }

    @ResponseBody
    @RequestMapping("/store/app/{id}")
    public Object getApp(@PathVariable String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("app", this.storeService.getApp(id));
        map.put("install", this.storeService.check(id));
        map.put("reviews", this.storeService.getReviews(id, 10, 1));
        return map;
    }

    @ResponseBody
    @RequestMapping("/store/search")
    public Object search(@RequestParam String key, Integer size, Integer page) {
        return this.storeService.search(key, size, page);
    }

    @ResponseBody
    @RequestMapping("/store/app/install")
    public Result<WebApp> install(String appId) {
        return this.success(this.installationService.install(appId));
    }

    @RequestMapping("/store/app/review")
    public @ResponseBody
    Object review(String appId, Integer score, String review) {
        this.reviewService.review(appId, score, review);
        return this.success();
    }
}
