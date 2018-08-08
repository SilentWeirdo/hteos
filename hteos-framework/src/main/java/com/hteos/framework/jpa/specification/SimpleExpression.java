package com.hteos.framework.jpa.specification;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 简单条件表达式
 *
 * @author lee
 */
public class SimpleExpression<T> implements Specification<T> {

    private String property;
    private Object value;
    private Operator operator;

    protected SimpleExpression(String property, Object value, Operator operator) {
        this.property = property;
        this.value = value;
        this.operator = operator;
    }

    public String getProperty() {
        return property;
    }

    public Object getValue() {
        return value;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        Expression expression = ExpressionUtils.getExpression(root, property);
        switch (operator) {
            case EQ:
                return builder.equal(expression, value);
            case NE:
                return builder.notEqual(expression, value);
            case LT:
                return builder.lessThan(expression, (Comparable) value);
            case GT:
                return builder.greaterThan(expression, (Comparable) value);
            case LTE:
                return builder.lessThanOrEqualTo(expression, (Comparable) value);
            case GTE:
                return builder.greaterThanOrEqualTo(expression, (Comparable) value);
            case IS_NULL:
                return builder.isNull(expression);
            case NOT_NULL:
                return builder.isNotNull(expression);
            case IN:
                return builder.in(expression).value(value);
            default:
                return null;
        }
    }
}