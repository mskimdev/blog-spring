package com.test.blog_spring._core.config;

import com.test.blog_spring._core.interceptor.LoginInterceptor;
import com.test.blog_spring._core.interceptor.SessionInterceptor;
import com.test.blog_spring.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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

    // 정적 리소스 핸들러 설정
    // 외부 사용자가 내 서버 컴퓨터에 특정 경로를 바로 화깅ㄴ할 수 있게 한다면
    // 보안상 취약할 수 있음
    // 설정 경로를 찾을 수 없게함
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + FileUtil.IMG_UPLOAD_DIR + "/");
    }
}
