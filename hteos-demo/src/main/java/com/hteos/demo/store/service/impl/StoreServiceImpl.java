package com.hteos.demo.store.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hteos.biz.app.entity.App;
import com.hteos.biz.app.repository.AppRepository;
import com.hteos.biz.installation.repository.InstallationRepository;
import com.hteos.biz.review.entity.Review;
import com.hteos.biz.review.repository.ReviewRepository;
import com.hteos.biz.user.entity.User;
import com.hteos.biz.utils.UserContext;
import com.hteos.demo.store.model.StoreApp;
import com.hteos.demo.store.service.StoreService;
import com.hteos.framework.support.repository.CommonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private InstallationRepository installationRepository;

    @Override
    @JsonIgnoreProperties({"task", "tile"})
    public Page<StoreApp> getCategory(String category, Integer size, Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page pageResult = this.appRepository.findByCategory(category, pageRequest);
        return pageResult.map((Function<App, StoreApp>) app -> {
            StoreApp storeApp = new StoreApp();
            BeanUtils.copyProperties(app, storeApp);
            return storeApp;
        });
    }

    @Override
    public Page<StoreApp> search(String key, Integer size, Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page pageResult = this.appRepository.findByNameContainsAndCodeContainsAndSubjectContains(key, key, key, pageRequest);
        return pageResult.map((Function<App, StoreApp>) app -> {
            StoreApp storeApp = new StoreApp();
            BeanUtils.copyProperties(app, storeApp);
            return storeApp;
        });
    }

    @Override
    public List<StoreApp> getHotChannel(Integer size) {
        PageRequest pageRequest = PageRequest.of(0, 8, Sort.Direction.DESC, "score", "installCount", "reviewCount");
        return this.appRepository.findAll(pageRequest).getContent().stream().map(app -> {
            StoreApp storeApp = new StoreApp();
            BeanUtils.copyProperties(app, storeApp);
            return storeApp;
        }).collect(Collectors.toList());
    }

    @Override
    @JsonIgnoreProperties({"task", "tile"})
    public List<StoreApp> getInstallChannel(Integer size) {
        PageRequest pageRequest = PageRequest.of(0, 8, Sort.Direction.DESC, "installCount");
        return this.appRepository.findAll(pageRequest).getContent().stream().map(app -> {
            StoreApp storeApp = new StoreApp();
            BeanUtils.copyProperties(app, storeApp);
            return storeApp;
        }).collect(Collectors.toList());
    }

    @Override
    public List<StoreApp> getScoreChannel(Integer size) {
        PageRequest pageRequest = PageRequest.of(0, 8, Sort.Direction.DESC, "score");
        return this.appRepository.findAll(pageRequest).getContent().stream().map(app -> {
            StoreApp storeApp = new StoreApp();
            BeanUtils.copyProperties(app, storeApp);
            return storeApp;
        }).collect(Collectors.toList());
    }

    @Override
    public List<StoreApp> getNewChannel(Integer size) {
        PageRequest pageRequest = PageRequest.of(0, 8, Sort.Direction.DESC, "releaseDate");
        return this.appRepository.findAll(pageRequest).getContent().stream().map(app -> {
            StoreApp storeApp = new StoreApp();
            BeanUtils.copyProperties(app, storeApp);
            return storeApp;
        }).collect(Collectors.toList());
    }

    @Override
    public StoreApp getApp(String id) {
        App app = this.appRepository.findById(id).get();
        StoreApp storeApp = new StoreApp();
        BeanUtils.copyProperties(app, storeApp);
        return storeApp;
    }


    @Override
    public boolean check(String id) {
        if (UserContext.getUser() == null) {
            return false;
        }
        return (this.installationRepository.getByApp_IdAndUser_Id(id, UserContext.getUserId()) != null);
    }

    @Override
    public Map<String, Object> getReviews(String appId, Integer size, Integer page) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        Page<Review> pageResult = this.reviewRepository.findByApp_Id(appId, PageRequest.of(page, size));
        List<Review> reviews = pageResult.getContent();
        for (Review review : reviews) {
            map = new HashMap<>();
            User user = review.getUser();
            map.put("account", user.getNickName());
            map.put("face", user.getFace());
            map.put("score", review.getScore());
            map.put("reviewDate", review.getReviewDate().getTime());
            map.put("review", review.getReview());
            list.add(map);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("reviews", list);
        resultMap.put("total", pageResult.getTotalElements());
        return resultMap;
    }
}
