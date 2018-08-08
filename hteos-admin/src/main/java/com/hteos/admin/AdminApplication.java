package com.hteos.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author LIQIU
 * @date 2018-6-25
 **/
@ComponentScan("com.hteos")
@SpringBootApplication
@EnableTransactionManagement
@EntityScan("com.hteos")
@EnableJpaRepositories(basePackages = "com.hteos")
@Configuration
@EnableWebSecurity
public class AdminApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login.html").loginProcessingUrl("/login")
                .defaultSuccessUrl("/index.html").failureUrl("/login.html?error=true")
                .and().authorizeRequests().antMatchers("/login.html", "/img/**", "/css/**").permitAll()
                .and().logout().permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable();
    }
}