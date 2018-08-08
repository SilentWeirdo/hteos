package com.hteos.demo.store.service;

import com.hteos.demo.store.model.StoreApp;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface StoreService {

    @Transactional(readOnly = true)
    List<StoreApp> getHotChannel(Integer size);

    @Transactional(readOnly = true)
    List<StoreApp> getInstallChannel(Integer size);

    @Transactional(readOnly = true)
    List<StoreApp> getScoreChannel(Integer size);

    @Transactional(readOnly = true)
    List<StoreApp> getNewChannel(Integer size);

    @Transactional(readOnly = true)
    StoreApp getApp(String id);

    @Transactional(readOnly = true)
    boolean check(String id);

    @Transactional(readOnly = true)
    Map<String, Object> getReviews(String appId, Integer size, Integer page);

    @Transactional(readOnly = true)
    Page getCategory(String category, Integer size, Integer page);

    @Transactional(readOnly = true)
    Page<StoreApp> search(String key, Integer size, Integer page);
}
