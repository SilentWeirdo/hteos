package com.hteos.biz.installation.dto;

import lombok.Data;

/**
 * @author LIQIU
 * @date 2018-7-17
 **/
@Data
public class WebAppTile {

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

    private String template;

    private String templateUrl;

    private String dataUrl;

    private String dependencies;

}
