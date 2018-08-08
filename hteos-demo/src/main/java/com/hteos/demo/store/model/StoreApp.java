package com.hteos.demo.store.model;

import lombok.Data;

import java.util.Date;

/**
 * @author LIQIU
 * @date 2018-7-31
 **/
@Data
public class StoreApp {


    /**
     * 主键ID
     * */
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
}
