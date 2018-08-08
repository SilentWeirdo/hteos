package com.hteos.framework.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author LIQIU
 * @date 2018-5-14
 **/
@NoRepositoryBean
public interface BaseRepository <T> extends JpaRepository<T,String>,JpaSpecificationExecutor<T> {
}
