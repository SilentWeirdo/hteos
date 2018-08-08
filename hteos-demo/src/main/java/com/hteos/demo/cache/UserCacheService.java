package com.hteos.demo.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.hteos.biz.user.entity.User;
import com.hteos.biz.user.service.UserService;
import com.hteos.biz.model.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author LIQIU
 * @date 2018-7-18
 **/
@Service
public class UserCacheService {

    @Autowired
    private UserService userService;

    private LoadingCache<String, UserVo> caches;

    @PostConstruct
    public void init() {
        caches = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(60 * 24, TimeUnit.MINUTES)
                .build(new CacheLoader<String, UserVo>() {
                    @Override
                    public UserVo load(String key) throws Exception {
                        User user = userService.get(key);
                        UserVo userVo = new UserVo();
                        userVo.setLogon(true);
                        BeanUtils.copyProperties(user, userVo);
                        return userVo;
                    }
                });
    }

    public UserVo get(String id) throws ExecutionException {
        return this.caches.get(id);
    }

    public void put(UserVo userVo) {
        this.caches.put(userVo.getId(), userVo);
    }

}
