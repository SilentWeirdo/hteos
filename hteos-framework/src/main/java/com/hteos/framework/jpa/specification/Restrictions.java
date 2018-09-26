package com.hteos.framework.jpa.specification;

import org.hibernate.criterion.MatchMode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

/**
 * 条件构造器
 * 用于创建条件表达式
 *
 * @Class Name Restrictions
 * @Author lee
 */
public class Restrictions {


    /**
     * 为空
     *
     * @param property
     * @return
     */
    public static <T> RangeExpression<T> between(String property, Comparable start, Comparable end) {
        return new RangeExpression<T>(property, start, end);
    }


    /**
     * 为空
     *
     * @param property
     * @return
     */
    public static <T> SimpleExpression<T> isNull(String property) {
        return new SimpleExpression<>(property, null, Operator.IS_NULL);
    }

    /**
     * 不为空
     *
     * @param property
     * @return
     */
    public static <T> SimpleExpression<T> notNull(String property) {
        return new SimpleExpression<>(property, null, Operator.NOT_NULL);
    }

    /**
     * 等于
     *
     * @param property
     * @param value
     * @return
     */
    public static <T> SimpleExpression<T> eq(String property, Object value) {
        return new SimpleExpression<>(property, value, Operator.EQ);
    }

    /**
     * 不等于
     *
     * @param property
     * @param value
     * @return
     */
    public static <T> SimpleExpression<T> ne(String property, Object value) {
        return new SimpleExpression<>(property, value, Operator.NE);
    }

    /**
     * 模糊匹配
     *
     * @param property
     * @param value
     * @return
     */
    public static <T> MatchExpression<T> like(String property, String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new MatchExpression<>(property, value, MatchMode.ANYWHERE, false);
    }


    /**
     * @param property
     * @param value
     * @param matchMode
     * @return
     */
    public static <T> MatchExpression<T> notLike(String property, String value, MatchMode matchMode) {
        return new MatchExpression<>(property, value, matchMode, true);
    }

    /**
     * 模糊匹配
     *
     * @param property
     * @param value
     * @param matchMode
     * @return
     */
    public static <T> MatchExpression<T> like(String property, String value, MatchMode matchMode) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new MatchExpression<>(property, value, matchMode, false);
    }


    /**
     * 大于
     *
     * @param property
     * @param value
     * @param ignoreNull
     * @return
     */
    public static <T> SimpleExpression<T> gt(String property, Object value, boolean ignoreNull) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression<>(property, value, Operator.GT);
    }

    /**
     * 小于
     *
     * @param property
     * @param value
     * @param ignoreNull
     * @return
     */
    public static <T> SimpleExpression<T> lt(String property, Object value, boolean ignoreNull) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression<>(property, value, Operator.LT);
    }

    /**
     * 大于等于
     *
     * @param property
     * @param value
     * @param ignoreNull
     * @return
     */
    public static <T> SimpleExpression<T> lte(String property, Object value, boolean ignoreNull) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression<>(property, value, Operator.GTE);
    }

    /**
     * 小于等于
     *
     * @param property
     * @param value
     * @param ignoreNull
     * @return
     */
    public static <T> SimpleExpression<T> gte(String property, Object value, boolean ignoreNull) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression<>(property, value, Operator.LTE);
    }

    /**
     * 并且
     *
     * @param specifications
     * @return
     */
    public static <T> LogicalExpression<T> and(Specification... specifications) {
        return new LogicalExpression<>(specifications, Operator.AND);
    }

    /**
     * 或者
     *
     * @param specifications
     * @return
     */
    public static <T> LogicalExpression<T> or(Specification... specifications) {
        return new LogicalExpression<>(specifications, Operator.OR);
    }

    /**
     * 包含于
     *
     * @param property
     * @param value
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> SimpleExpression<T> in(String property, Collection value) {
        return new SimpleExpression<>(property, value, Operator.IN);
    }

}