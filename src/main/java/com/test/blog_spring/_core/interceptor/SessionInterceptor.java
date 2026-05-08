package com.test.blog_spring._core.interceptor;

import com.test.blog_spring.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

        if(modelAndView != null){
            HttpSession session = request.getSession(false);
            if(session != null){
                User sessionUser = (User)session.getAttribute("sessionUser");
                if(sessionUser != null){
                    modelAndView.addObject("sessionUser", sessionUser);
                }

            }
        }


    }
}
