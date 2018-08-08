package com.hteos.framework.support.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Zhang Kaitao
 * 
 * @version 1.0, 2010-8-12
 */
public abstract class BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 2035013017939483936L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	// 创建日期
	protected Date createDate;
	// 更新日期
	protected Date lastUpdateDate;
	// 创建人
	protected String createBy;
	// 更新人
	protected String lastUpdateBy;
	
	protected String lastUpdateIp;
	
	// 版本号
	protected Integer version;
	// 是否删除
	protected String deleted = "N";


	@Column(name = "CREATE_DATE")
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the lastUpadteDate
	 */
	@Column(name="LAST_UPDATE_DATE")
	@JsonIgnore
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	/**
	 * @return the createBy
	 */
	@Column(name="CREATE_BY")
	@JsonIgnore
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the lastUpdateBy
	 */
	@Column(name="LAST_UPDATE_BY")
	@JsonIgnore
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	/**
	 * @return the lastUpdateIp
	 */
	@Column(name="LAST_UPDATE_IP")
	@JsonIgnore
	public String getLastUpdateIp() {
		return lastUpdateIp;
	}

	/**
	 * @param lastUpdateIp the lastUpdateIp to set
	 */
	public void setLastUpdateIp(String lastUpdateIp) {
		this.lastUpdateIp = lastUpdateIp;
	}

	/**
	 * @return the version
	 */
	@Version
	@Column(name = "VERSION")
	@JsonIgnore
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the deleted
	 */
	@Column(name = "DELETED")
	@JsonIgnore
	public String getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
}
