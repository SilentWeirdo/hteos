package com.hteos.framework.jpa.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LIQIU
 * @date 2018-5-14
 **/
public class MultiSpecification<T> implements Specification<T> {

    private List<Specification<T>> specifications = new ArrayList<Specification<T>>();

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        if (!specifications.isEmpty()) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for (Specification<T> specification : specifications) {
                predicates.add(specification.toPredicate(root, query, builder));
            }
            // 将所有条件用 and 联合起来
            if (predicates.size() > 0) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }
        return builder.conjunction();
    }

    /**
     * 增加简单条件表达式
     *
     * @Methods Name add
     * @Create In 2012-2-8 By lee
     */
    public void add(Specification<T> specification) {
        if (specification != null) {
            specifications.add(specification);
        }
    }

    public static <T> MultiSpecification<T> newInstance() {
        return new MultiSpecification<>();
    }
}
