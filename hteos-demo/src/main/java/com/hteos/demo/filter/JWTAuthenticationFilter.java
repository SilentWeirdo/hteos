package com.hteos.demo.filter;

import com.hteos.biz.utils.JwtUtils;
import com.hteos.biz.utils.UserContext;
import com.hteos.demo.cache.UserCacheService;
import org.apache.catalina.connector.Request;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * JWT认证Filter
 *
 * @author LIQIU
 * @date 2018-7-18
 **/
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private UserCacheService userCacheService;

    public JWTAuthenticationFilter(UserCacheService userCacheService) {
        this.userCacheService = userCacheService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token)) {
            String subject = JwtUtils.parse(token);
            if (subject != null) {
                try {UserContext
                    UserContext.put(userCacheService.get(subject));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }*/

        filterChain.doFilter(request, response);
    }
}
