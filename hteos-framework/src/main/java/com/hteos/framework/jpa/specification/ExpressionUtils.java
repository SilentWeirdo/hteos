package com.hteos.framework.jpa.specification;

import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * @author LIQIU
 * @date 2018-5-21
 **/
public class ExpressionUtils {

    private static final String DELIMITER = ".";

    public static Expression getExpression(Root<?> root, String property) {
        Path expression;
        if (property.contains(DELIMITER)) {
            String[] names = StringUtils.split(property, DELIMITER);
            expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }
        } else {
            expression = root.get(property);
        }
        return expression;
    }
}
