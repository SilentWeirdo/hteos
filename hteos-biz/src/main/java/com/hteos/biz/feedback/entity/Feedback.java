package com.hteos.biz.feedback.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;

import com.hteos.framework.support.entity.BaseEntity;

@Entity
@Table(name="hteos_feedback")
public class Feedback extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String email;
	
	private String name;
	
	private String content;
	
	private String ip;
	
	private String time;
	
	private String day;

	@Id
	@GeneratedValue(generator="idGenerator")
	@GenericGenerator(name="idGenerator", strategy="uuid")
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Column
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Column
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	@Column
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column
	@IndexColumn(name="feedback_day_idx")
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
}
