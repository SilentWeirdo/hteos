package com.hteos.biz.user.service;

import com.hteos.biz.user.entity.User;
import com.hteos.framework.exception.account.IllegalCertificateException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional(readOnly = true)
    Page<User> list(String key, Integer size, Integer page);

    void doModifyPassword(String account, String oldPassword,
                          String newPassword) throws IllegalCertificateException;

    User doSaveAccountInfo(String nickName, String residence);

    void doSaveFace(String face);

    void doSaveWallpaper(String wallpaper);

    void doSaveTheme(String theme);

    void doSaveMode(String mode);

    void doSaveShortcutSize(String shortcutSize);

    User get(String id);

}
