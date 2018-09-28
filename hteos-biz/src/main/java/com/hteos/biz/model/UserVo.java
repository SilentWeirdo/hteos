package com.hteos.biz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author LIQIU
 * @date 2018-7-18
 **/
@Data
public class UserVo {

    private String id;

    private String username;

    private String email;

    private String nickName;

    private String face;

    private String wallpaper;

    private String theme;

    private String mode;

    private String shortcutSize;

    private String residence;

    private Boolean logon;

    private String token;

    @JsonIgnore
    private String password;

}
