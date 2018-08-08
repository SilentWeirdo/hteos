package com.hteos.framework.jpa.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author LIQIU
 * @date 2018-5-14
 **/
@Data
@AllArgsConstructor
public class SortableSpecification<T> {

    private Specification<T> specification;

    private Sort sort;

    /**
     * 创建 SortableSpecification
     * @param specification
     * @param sort
     * @param <T>
     * @return
     */
    public static <T> SortableSpecification<T> of(Specification<T> specification, Sort sort){
        return new SortableSpecification<>(specification,sort);
    }
}
