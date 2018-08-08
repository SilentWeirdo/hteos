package com.hteos.demo;

import com.hteos.demo.cache.UserCacheService;
import com.hteos.demo.filter.JWTAuthenticationFilter;
import com.hteos.framework.interceptor.http.HttpRequestContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;

/**
 * @author LIQIU
 * @date 2018-6-25
 **/
@Configuration
@ComponentScan("com.hteos")
@SpringBootApplication
@EnableTransactionManagement
@EntityScan("com.hteos")
@EnableJpaRepositories(basePackages = "com.hteos")
public class DemoApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpRequestContextInterceptor()).addPathPatterns("/*");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowCredentials(true).allowedHeaders("*")
                .allowedMethods("*").allowedOrigins("*");
    }

    @Bean
    public FilterRegistrationBean<JWTAuthenticationFilter> jwtAuthenticationFilter(UserCacheService userCacheService){
        FilterRegistrationBean<JWTAuthenticationFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterFilterRegistrationBean.setFilter(new JWTAuthenticationFilter(userCacheService));
        return filterFilterRegistrationBean;
    }
}

