package com.hteos.framework.jpa.specification;

import org.hibernate.criterion.MatchMode;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * @author LIQIU
 * @date 2018-5-21
 **/
public class MatchExpression<T> implements Specification<T> {

    private String property;
    private Object value;
    private MatchMode matchMode;
    private boolean reverse;


    protected MatchExpression(String property, Object value, MatchMode matchMode, boolean reverse) {
        this.property = property;
        this.value = value;
        this.matchMode = matchMode;
        this.reverse = reverse;
    }

    public String getProperty() {
        return property;
    }

    public Object getValue() {
        return value;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        Expression expression = ExpressionUtils.getExpression(root, property);
        if (reverse) {
            return builder.notLike(expression, matchMode.toMatchString(String.valueOf(value)));
        } else {
            return builder.like(expression, matchMode.toMatchString(String.valueOf(value)));
        }
    }
}
