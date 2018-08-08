package com.hteos.biz.user.repository;

import com.hteos.biz.user.entity.User;
import com.hteos.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
public interface UserRepository extends BaseRepository<User> {

    User getByEmail(String email);

    List<User> findByUsernameOrEmail(String username, String email);

    Page<User> findByUsernameContainsOrEmailContains(String username, String email, Pageable pageable);

    User getByUsernameOrEmail(String account, String email);

    User getByQqOpenId(String qqOpenId);

    User getByUsername(String username);
}
