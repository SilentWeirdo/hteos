package com.hteos.biz.app.entity;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.hteos.framework.support.entity.BaseEntity;

/**
 * @author LIQIU
 */
@Entity
@Table(name="hteos_app")
@Data
public class App extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 * */
    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	private String id;


    private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 显示类型 图标
	 */
	private String icon;
	

	/**
	 * 背景图片
	 */
	private String image;

	/**
	 * 描述
	 */
    @Column(name="app_desc")
	private String desc;
	
	/**
	 * 简述
	 */
	private String subject;
	
	/**
	 * 版本
	 */
	private String appVersion;
	
	/**
	 * 发布日期
	 */
	private Date releaseDate;
	
	/**
	 * 提供者
	 */
	private String provider;


	
	/**
	 *状态1、配置中 2、审批中 3、已发布  4、已过期
	 */
	private String status;

	
	private Boolean isDefault;
	
	private String category;
	
	private Integer installCount;
	
	private Integer score;
	
	private Integer reviewCount;
	
	private Boolean isNative;

	@OneToOne(targetEntity = Tile.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Tile tile;

    @OneToOne(targetEntity = Task.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Task task;

}
