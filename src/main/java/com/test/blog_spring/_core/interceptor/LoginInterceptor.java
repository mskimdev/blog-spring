package com.test.blog_spring._core.interceptor;

import com.test.blog_spring._core.errors.Exception401;
import com.test.blog_spring.dto.UserResponse;
import com.test.blog_spring.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 인증 검사
        User sessionUser = (User)request.getSession().getAttribute("sessionUser");
        if(sessionUser == null) {
            throw new Exception401("로그인 먼저 해주세요.");
        }

        return true;
    }
}
