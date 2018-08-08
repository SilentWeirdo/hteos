package com.hteos.framework.jpa.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 逻辑条件表达式 用于复杂条件时使用，如但属性多对应值的OR查询等 
 * @author lee 
 * 
 */  
public class LogicalExpression<T> implements Specification<T> {
    private Specification[] specifications;
    private Operator operator;
  
    public LogicalExpression(Specification[] specifications, Operator operator) {
        this.specifications = specifications;
        this.operator = operator;  
    }  
  
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        for(int i=0;i<this.specifications.length;i++){
            predicates.add(this.specifications[i].toPredicate(root, query, builder));
        }  
        switch (operator) {  
        case OR:  
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        default:  
            return null;  
        }  
    }  
  
}  