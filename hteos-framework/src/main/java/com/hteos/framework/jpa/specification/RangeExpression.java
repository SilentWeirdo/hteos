package com.hteos.framework.jpa.specification;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 简单条件表达式
 *
 * @author lee
 */
public class RangeExpression<T> implements Specification<T> {

    private String property;
    private Object start;
    private Object end;

    protected RangeExpression(String property, Object start, Object end) {
        this.property = property;
        this.start = start;
        this.end = end;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {

        Expression expression = ExpressionUtils.getExpression(root, property);
        return builder.between(expression, (Comparable) start, (Comparable) end);

    }
}