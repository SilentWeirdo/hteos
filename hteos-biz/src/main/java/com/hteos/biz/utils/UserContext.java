package com.hteos.biz.utils;

import com.hteos.biz.model.UserVo;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author LIQIU
 * @date 2018-6-27
 **/
public class UserContext {

    private static String userAttributeName = "hteos.user";

    private static ThreadLocal<UserVo> holder = new NamedThreadLocal<UserVo>("userHolder");

    public static void put(UserVo userVo) {
        //holder.set(userVo);
        getSession().setAttribute(userAttributeName, userVo);
    }

    public static UserVo getUser() {
        return (UserVo) getSession().getAttribute(userAttributeName);
        //return holder.get();
    }

    public static HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession();
    }


    public static String getUserId() {
        UserVo userVo = getUser();
        return userVo != null ? userVo.getId() : null;
    }

    public static void clear() {
        holder.remove();
    }

    public static boolean isLoggedIn() {
        return getUser() != null;
    }
}
