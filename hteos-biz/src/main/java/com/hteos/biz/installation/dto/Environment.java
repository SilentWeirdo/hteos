package com.hteos.biz.installation.dto;

import com.hteos.biz.model.UserVo;
import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * @author LIQIU
 * @date 2018-7-17
 **/
@Data
public class Environment {

    private Collection<WebGroup> groups;

    private List<Favorite> favorites;

    private UserVo preference;

}
