package com.hteos.biz.user.service.impl;

import com.hteos.biz.user.entity.User;
import com.hteos.biz.user.repository.UserRepository;
import com.hteos.biz.user.service.UserService;
import com.hteos.biz.utils.UserContext;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User get(String id) {
        return this.userRepository.findById(id).get();
    }

    @Override
    public void doModifyPassword(String username, String oldPassword,
                                 String newPassword) {
        User user = userRepository.getByUsername(username);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public User doSaveAccountInfo(String nickName, String residence) {
        User user = this.userRepository.findById(UserContext.getUser().getId()).get();
        user.setNickName(nickName);
        user.setResidence(residence);
        this.userRepository.save(user);
        return user;
    }

    @Override
    public void doSaveFace(String face) {
        User user = this.userRepository.findById(UserContext.getUserId()).get();
        user.setFace(face);
        this.userRepository.save(user);
    }

    @Override
    public void doSaveWallpaper(String wallpaper) {
        User user = this.userRepository.findById(UserContext.getUserId()).get();
        user.setWallpaper(wallpaper);
        this.userRepository.save(user);
    }

    @Override
    public void doSaveTheme(String theme) {
        User user = this.userRepository.findById(UserContext.getUserId()).get();
        user.setTheme(theme);
        this.userRepository.save(user);
    }

    @Override
    public void doSaveMode(String mode) {
        User user = this.userRepository.findById(UserContext.getUserId()).get();
        user.setMode(mode);
        this.userRepository.save(user);
    }

    @Override

    public void doSaveShortcutSize(String shortcutSize) {
        User user = this.userRepository.findById(UserContext.getUserId()).get();
        user.setShortcutSize(shortcutSize);
        this.userRepository.save(user);
    }

    @Override
    public Page<User> list(String key, Integer size, Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("registerDate")));
        if(StringUtils.isNotEmpty(key)){
            return this.userRepository.findByUsernameContainsOrEmailContains(key, key, pageRequest);
        }else{
            return this.userRepository.findAll(pageRequest);
        }

    }
}
