package com.hteos.biz.review.service.impl;

import com.hteos.biz.app.entity.App;
import com.hteos.biz.app.repository.AppRepository;
import com.hteos.biz.review.entity.Review;
import com.hteos.biz.review.repository.ReviewRepository;
import com.hteos.biz.review.service.ReviewService;
import com.hteos.biz.user.repository.UserRepository;
import com.hteos.biz.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void review(String appId, Integer score, String review) {
        App app = this.appRepository.findById(appId).get();

        Review appReview = new Review();
        appReview.setUser(userRepository.findById(UserContext.getUserId()).get());
        appReview.setApp(app);
        appReview.setReview(review);
        appReview.setScore(score);
        appReview.setReviewDate(new Date());
        this.reviewRepository.save(appReview);


        Integer reviewCount = app.getReviewCount();
        reviewCount = reviewCount == null ? 0 : reviewCount + 1;
        app.setReviewCount(reviewCount);

        Double avgScore = this.reviewRepository.getAppAvgScore(appId);
        avgScore = avgScore == null ? score.doubleValue() : avgScore;
        avgScore = Math.ceil(avgScore);
        app.setScore(avgScore.intValue());
        this.appRepository.save(app);
    }
}
