package com.hteos.biz.installation.dto;

import lombok.Data;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
@Data
public class AppDto {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;


    private WebAppTile tile;

    private WebAppTask task;

    /**
     * 显示类型 图标
     */
    private String icon;


    /**
     * 背景图片
     */
    private String image;

    private Boolean isNative;

    private String code;

    private Boolean hidden;

    private String provider;

    private String appVersion;

    private String subject;

    private String category;

}
