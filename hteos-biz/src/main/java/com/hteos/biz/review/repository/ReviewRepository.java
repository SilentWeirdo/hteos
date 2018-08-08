package com.hteos.biz.review.repository;

import com.hteos.biz.review.entity.Review;
import com.hteos.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
public interface ReviewRepository extends BaseRepository<Review> {

    @Query("select avg(a.score) from Review a where a.app.id = :appId")
    Double getAppAvgScore(String appId);

    Page<Review> findByApp_Id(String appId, Pageable pageable);

}
