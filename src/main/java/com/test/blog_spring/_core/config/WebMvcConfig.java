package com.test.blog_spring._core.config;

import com.test.blog_spring._core.interceptor.LoginInterceptor;
import com.test.blog_spring._core.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**"); // all

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/board/**", "/user/**")
                .excludePathPatterns(
                        // Main Page
                        "/",

                        // User
                        "/login-form",
                        "/join-form",
                        "/logout",

                        // Board
                        "/board/list",
                        "/board/{id:\\d+}",

                        // H2
                        "/h2-console/**"
                );
    }
}
