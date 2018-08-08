package com.hteos.biz.app.model;

import com.hteos.biz.app.entity.Task;
import com.hteos.biz.app.entity.Tile;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author LIQIU
 * @date 2018-7-31
 **/
@Data
public class AdminApp {
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

    private Tile tile;

    private Task task;
}
