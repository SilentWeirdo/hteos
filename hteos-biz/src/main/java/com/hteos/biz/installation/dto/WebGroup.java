package com.hteos.biz.installation.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author LIQIU
 * @date 2018-6-26
 **/
@Data
public class WebGroup {

    private String id;

    private String name;

    private Integer index;

    private List<WebApp> apps = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebGroup)) {
            return false;
        }
        WebGroup webGroup = (WebGroup) o;
        return Objects.equals(id, webGroup.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, index);
    }
}
