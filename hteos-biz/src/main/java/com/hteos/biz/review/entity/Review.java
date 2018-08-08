package com.hteos.biz.review.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hteos.biz.user.entity.User;
import com.hteos.biz.app.entity.App;
import org.hibernate.annotations.GenericGenerator;

import com.hteos.framework.support.entity.BaseEntity;

@Entity
@Table(name = "hteos_review")
public class Review extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private User user;
	
	private App app;
	
	private String review;
	
	private Integer score;
	
	private Date reviewDate;

	@Id
	@GeneratedValue(generator="idGenerator")
	@GenericGenerator(name="idGenerator", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(targetEntity=App.class,fetch=FetchType.LAZY)
	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	@Column(length=500)
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Column
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column
	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
}
