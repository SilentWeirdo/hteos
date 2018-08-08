package com.hteos.biz.installation.entity;

import java.util.Date;

import javax.persistence.*;

import com.hteos.biz.user.entity.User;
import com.hteos.biz.app.entity.App;
import com.hteos.biz.group.entity.Group;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.hteos.framework.support.entity.BaseEntity;

@Entity
@Data
@Table(name = "hteos_installation")
public class Installation extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;

    @OneToOne(targetEntity = App.class, fetch = FetchType.LAZY)
    private App app;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    private Date installDate;

    @Column(name = "app_index")
    private Integer index;

    private Boolean hidden;

    private String name;

    private Boolean favorited;

    private String size;

    private Integer favoriteIndex;

    @OneToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private Integer shortcutIndex;
}
