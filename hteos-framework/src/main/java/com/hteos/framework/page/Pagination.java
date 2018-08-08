package com.hteos.framework.page;

import lombok.Data;

/**
 * @author LIQIU
 * @date 2018-4-17
 **/
@Data
public class Pagination {
    /**
     * 升序
     */
    public final static String ASC = "asc";
    /**
     * 降序
     */
    public final static String DESC = "desc";

    private Integer pageSize;
    private Integer pageNumber;
    private String orderBy;
    private String orderDirection ;

}
