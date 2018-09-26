package com.hteos.biz.app.repository;

import com.hteos.biz.app.entity.App;
import com.hteos.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
public interface AppRepository extends BaseRepository<App> {

	/**
	 * 根据名字模糊查询
	 *
	 * @param name
	 * @param pageable
	 * @return Page<App>
	 */
	Page<App> findByNameContains(String name, Pageable pageable);

	Page<App> findByCategory(String category, Pageable pageable);

	Page<App> findByNameContainingOrCodeContainingOrSubjectContaining(String name, String code, String subject, Pageable pageable);
}
