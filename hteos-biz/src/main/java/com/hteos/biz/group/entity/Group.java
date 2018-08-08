package com.hteos.biz.group.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hteos.biz.user.entity.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.hteos.framework.support.entity.BaseEntity;

@Entity
@Data
@Table(name = "hteos_group")
public class Group extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -7738126208189011162L;

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;

    private String name;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "group_index")
    private Integer index;
}
