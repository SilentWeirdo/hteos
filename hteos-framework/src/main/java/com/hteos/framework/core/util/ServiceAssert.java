package com.hteos.framework.core.util;

import com.hteos.framework.core.constant.ErrorStatus;
import com.hteos.framework.core.exception.ServiceException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 服务断言，用于逻辑判断，抛出服务异常
 * @author LIQIU
 * @date 2018-1-21
 **/
public class ServiceAssert {

    /**
     * Assert a boolean expression, throwing an {@code IllegalArgumentException}
     * if the expression evaluates to {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     * @param expression a boolean expression
     * @param errorStatus
     * @throws ServiceException if {@code expression} is {@code false}
     */
    public static void isTrue(boolean expression, ErrorStatus errorStatus) {
        if (!expression) {
            throw new ServiceException(errorStatus);
        }
    }

    /**
     * Assert that an object is {@code null}.
     * <pre class="code">Assert.isNull(value, "The value must be null");</pre>
     * @param object the object to check
     * @param errorStatus
     * @throws ServiceException if the object is not {@code null}
     */
    public static void isNull(Object object,ErrorStatus errorStatus) {
        if (object != null) {
            throw new ServiceException(errorStatus);
        }
    }

    /**
     * Assert that an object is not {@code null}.
     * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
     * @param object the object to check
     * @param errorStatus
     * @throws ServiceException if the object is {@code null}
     */
    public static void notNull(Object object,ErrorStatus errorStatus) {
        if (object == null) {
            throw new ServiceException(errorStatus);
        }
    }

    /**
     * Assert that the given String is not empty; that is,
     * it must not be {@code null} and not the empty String.
     * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
     * @param text the String to check
     * @param errorStatus
     * @see StringUtils#hasLength
     * @throws ServiceException if the text is empty
     */
    public static void hasLength(String text,ErrorStatus errorStatus) {
        if (!StringUtils.hasLength(text)) {
            throw new ServiceException(errorStatus);
        }
    }


    /**
     * Assert that the given String contains valid text content; that is, it must not
     * be {@code null} and must contain at least one non-whitespace character.
     * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
     * @param text the String to check
     * @param errorStatus
     * @see StringUtils#hasText
     * @throws ServiceException if the text does not contain valid text content
     */
    public static void hasText(String text,ErrorStatus errorStatus) {
        if (!StringUtils.hasText(text)) {
            throw new ServiceException(errorStatus);
        }
    }


    /**
     * Assert that the given text does not contain the given substring.
     * <pre class="code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     * @param errorStatus
     * @throws ServiceException if the text contains the substring
     */
    public static void doesNotContain(String textToSearch, String substring,ErrorStatus errorStatus) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new ServiceException(errorStatus);
        }
    }


    /**
     * Assert that an array contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">Assert.notEmpty(array, "The array must contain elements");</pre>
     * @param array the array to check
     * @param errorStatus
     * @throws ServiceException if the object array is {@code null} or contains no elements
     */
    public static void notEmpty(Object[] array,ErrorStatus errorStatus) {
        if (ObjectUtils.isEmpty(array)) {
            throw new ServiceException(errorStatus);
        }
    }

    /**
     * Assert that an array contains no {@code null} elements.
     * <p>Note: Does not complain if the array is empty!
     * <pre class="code">Assert.noNullElements(array, "The array must contain non-null elements");</pre>
     * @param array the array to check
     * @param errorStatus
     * @throws ServiceException if the object array contains a {@code null} element
     */
    public static void noNullElements(Object[] array,ErrorStatus errorStatus) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new ServiceException(errorStatus);
                }
            }
        }
    }


    /**
     * Assert that a collection contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must contain elements");</pre>
     * @param collection the collection to check
     * @param errorStatus
     * @throws ServiceException if the collection is {@code null} or
     * contains no elements
     */
    public static void nmpty(Collection<?> collection, ErrorStatus errorStatus) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ServiceException(errorStatus);
        }
    }


    /**
     * Assert that a Map contains entries; that is, it must not be {@code null}
     * and must contain at least one entry.
     * <pre class="code">Assert.notEmpty(map, "Map must contain entries");</pre>
     * @param map the map to check
     * @param errorStatus
     * @throws ServiceException if the map is {@code null} or contains no entries
     */
    public static void notEmpty(Map<?, ?> map, ErrorStatus errorStatus) {
        if (CollectionUtils.isEmpty(map)) {
            throw new ServiceException(errorStatus);
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">Assert.instanceOf(Foo.class, foo, "Foo expected");</pre>
     * @param type the type to check against
     * @param obj the object to check
     * @param errorStatus
     * @throws ServiceException if the object is not an instance of type
     */
    public static void isInstanceOf(Class<?> type, Object obj,ErrorStatus errorStatus) {
        notNull(type, ErrorStatus.of(errorStatus.value(),"Type to check against must not be null"));
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, errorStatus.getMessage());
        }
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass, "Number expected");</pre>
     * @param superType the super type to check against
     * @param subType the sub type to check
     * @param errorStatus
     * @throws ServiceException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,ErrorStatus errorStatus) {
        notNull(superType, ErrorStatus.of(errorStatus.value(),"Super type to check against must not be null"));
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType,errorStatus);
        }
    }



    private static void instanceCheckFailed(Class<?> type, Object obj, String msg) {
        String className = (obj != null ? obj.getClass().getName() : "null");
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            }
            else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + ("Object of class [" + className + "] must be an instance of " + type);
        }
        throw new IllegalArgumentException(result);
    }

    private static void assignableCheckFailed(Class<?> superType, Class<?> subType,ErrorStatus errorStatus) {
        String result = "";
        String msg = errorStatus.getMessage();
        boolean defaultMessage = true;
        if (StringUtils.hasLength(errorStatus.getMessage())) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            }
            else {
                result = messageWithTypeName(msg, subType);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + (subType + " is not assignable to " + superType);
        }
        throw new ServiceException(errorStatus.value(),result);
    }

    private static boolean endsWithSeparator(String msg) {
        return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
    }

    private static String messageWithTypeName(String msg, Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }
}
