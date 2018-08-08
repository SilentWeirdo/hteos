package com.hteos.biz.feedback.service;

import com.hteos.biz.feedback.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface FeedbackService {

    @Transactional
    void saveFeedback(String email, String name, String content);

    @Transactional(readOnly = true)
    Page<Feedback> list(String day, Integer size, Integer page);
}
