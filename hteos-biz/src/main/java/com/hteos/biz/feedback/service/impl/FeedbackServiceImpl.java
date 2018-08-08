package com.hteos.biz.feedback.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hteos.biz.user.repository.UserRepository;
import com.hteos.biz.feedback.entity.Feedback;
import com.hteos.biz.feedback.repository.FeedbackRepository;
import com.hteos.biz.feedback.service.FeedbackService;
import com.hteos.biz.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hteos.biz.user.entity.User;
import com.hteos.framework.util.WebUtils;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveFeedback(String email, String name, String content) {
        Feedback feedback = new Feedback();
        User user = userRepository.findById(UserContext.getUserId()).get();
        feedback.setEmail(email);
        if (user != null) {
            feedback.setEmail(user.getEmail());
            feedback.setName(user.getNickName());
        }
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        feedback.setTime(format.format(date));
        feedback.setIp(WebUtils.getClientIp());
        feedback.setContent(content);
        feedback.setDay(format2.format(date));
        this.feedbackRepository.save(feedback);
    }

    @Override
    public Page<Feedback> list(String day, Integer size, Integer page) {
        return this.feedbackRepository.findByDay(day,PageRequest.of(page,size));
    }

}
