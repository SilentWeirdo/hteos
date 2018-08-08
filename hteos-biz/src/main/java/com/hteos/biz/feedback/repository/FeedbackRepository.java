package com.hteos.biz.feedback.repository;

import com.hteos.biz.feedback.entity.Feedback;
import com.hteos.framework.jpa.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author LIQIU
 * @date 2018-7-18
 **/
public interface FeedbackRepository extends BaseRepository<Feedback> {

    Page<Feedback> findByDay(String day, Pageable pageable);
}
