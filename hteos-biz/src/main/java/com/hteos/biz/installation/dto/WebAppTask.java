package com.hteos.biz.installation.dto;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author LIQIU
 * @date 2018-7-17
 **/
@Data
public class WebAppTask {

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
    private Integer left;

    /**
     * 默认位置
     */
    private Integer right;

    /**
     * 模板
     */
    private String template;

    /**
     * 模板地址
     */
    private String templateUrl;

    private String controller;

    private String dependencies;

}
