package com.hteos.biz.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author LIQIU
 * @date 2018-7-17
 **/
@Data
@Entity
@Table(name = "hteos_app_tile")
public class Tile {


    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;

    @JsonIgnore
    @OneToOne(targetEntity = App.class,mappedBy = "tile",fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    private App app;


    /**
     * 背景颜色
     */
    private String bgcolor;


    /**
     * 文本颜色
     */
    private String color;


    /**
     * 显示类型 1、中间显示图标  左下角显示名称
     * 2、中间显示图片 左下角显示名称
     * 3、中间显示图片 左下角显示图标
     * 4、中间显示图标
     * 5、中间显示图片
     */
    private String ui;

    /**
     * 尺寸大小
     */
    private String size;

    private String controller;

    @Column(length = 5000)
    private String template;

    private String templateUrl;

    @Column(length = 5000)
    private String dependencies;

}
