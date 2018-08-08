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
@Table(name = "hteos_app_task")
public class Task {

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;

    @JsonIgnore
    @OneToOne(targetEntity = App.class,mappedBy = "task",fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    private App app;

    /**
     *  任务类型：script 脚本 ,task 任务
     */
    private String type;

    /**
     * 执行脚本
     */
    @Column(length = 5000)
    private String script;

    /**
     * 运行任务的组件
     */
    private String shell;

    /**
     * 是否可最大化
     */
    private Boolean maximizable;

    /**
     * 是否最大化
     */
    private Boolean maximized;

    /**
     * 是否可更改大小
     */
    private Boolean resizable;

    /**
     * 默认宽度
     */
    private Integer width;

    /**
     * 默认高度
     */
    private Integer height;

    /**
     * 默认位置:
     */
    private Integer top;

    /**
     * 默认位置
     */
    private Integer bottom;

    /**
     * 默认位置
     */
    @Column(name="position_left")
    private Integer left;

    /**
     * 默认位置
     */
    @Column(name="position_right")
    private Integer right;

    /**
     * 模板
     */
    private String template;

    /**
     * 模板地址
     */
    private String templateUrl;

    @Column(length = 5000)
    private String dependencies;

    private String controller;

}
