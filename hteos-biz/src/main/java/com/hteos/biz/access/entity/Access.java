package com.hteos.biz.access.entity;

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
@Table(name="hteos_access")
public class Access extends BaseEntity{

	private String id;
	
	private String time;
	
	private String year;
	
	private String month;
	
	private String day;
	
	private String ip;
	
	private String user;
	
	private String province;
	
	private String city;
	
	private String userAgent;
	
	private Long accessCount;
	
	private Date date;
	
	private String referer;
	
	private Long duration;
	
	private String browser;
	
	private String browserVersion;
	
	private String os;
	
	private String device;

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
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column
	@IndexColumn(name = "access_year_idx")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column
	@IndexColumn(name = "access_month_idx")
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column
	@IndexColumn(name = "access_day_idx")
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Column
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Column
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Column
	public Long getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}

	@Column
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column
	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	@Column
	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}
	
	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
}
