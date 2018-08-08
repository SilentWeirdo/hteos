package com.hteos.biz.installation.service;

import com.hteos.biz.user.entity.User;
import com.hteos.biz.installation.dto.Environment;
import com.hteos.biz.installation.dto.WebApp;
import com.hteos.biz.installation.dto.WebGroup;
import com.hteos.biz.installation.entity.Installation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
public interface InstallationService {

    @Transactional(readOnly = true)
    Environment getEnvironment(String userId);

    @Transactional(readOnly = true)
    WebApp get(String id);

    @Transactional(propagation = Propagation.REQUIRED)
    void init(User user);

    @Transactional(readOnly = true)
    Installation check(String id);

    @Transactional
    WebApp install(String id);

    @Transactional
    void uninstall(String id) throws Exception;

    @Transactional
    void saveGroup(WebGroup webGroup);

    @Transactional
    void addFavorite(String id);

    @Transactional
    void deleteFavorite(String id);

    @Transactional
    void saveFavorites(List<Installation> installations);

    @Transactional
    void saveShortcutIndex(List<Installation> installations);

    @Transactional
    void hide(String id);

    @Transactional
    void show(String id);

    @Transactional
    void resize(String id, String size);
}
