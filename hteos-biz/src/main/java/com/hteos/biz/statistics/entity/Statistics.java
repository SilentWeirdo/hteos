package com.hteos.biz.statistics.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;

import com.hteos.framework.support.entity.BaseEntity;

@Entity
@Table(name="hteos_statistics")
public class Statistics extends BaseEntity{

	private String id;
	
	private String day;
	
	private Long accessCount = 0L;
	
	private Long resigterCount  = 0L;
	
	private Long ipCount = 0L;
	
	private Date date;

	@Id
	@GeneratedValue(generator="idGenerator")
	@GenericGenerator(name="idGenerator", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column
	@IndexColumn(name="Statistics_day_idx")
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Column
	public Long getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}

	@Column
	public Long getResigterCount() {
		return resigterCount;
	}

	public void setResigterCount(Long resigterCount) {
		this.resigterCount = resigterCount;
	}

	@Column
	public Long getIpCount() {
		return ipCount;
	}

	public void setIpCount(Long ipCount) {
		this.ipCount = ipCount;
	}

	@Column
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
