package com.hteos.biz.user.entity;

import com.hteos.framework.support.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "hteos_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;

    private String username;

    private String email;

    private String password;

    private String nickName;

    private String face;

    private String wallpaper;

    private String theme;

    private String qqOpenId;

    private String mode;

    private String shortcutSize;

    private String residence;

    private Date registerDate;
}
