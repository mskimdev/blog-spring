package com.test.blog_spring.controller;

import com.test.blog_spring.dto.UserRequest;
import com.test.blog_spring.entity.User;
import com.test.blog_spring.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService us;

    @GetMapping("/login-form")
    public String loginForm(){
        return "user/login-form";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession httpSession){
        user.validate();
        User loginUser = us.login(user.getUsername(), user.getPassword());
        loginUser.setPassword(null);
        httpSession.setAttribute("sessionUser", loginUser);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        User user = (User)httpSession.getAttribute("sessionUser");

        log.info("[로그아웃] username : {}", user.getUsername());

        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/user/detail")
    public String detail(Model mo, HttpSession session){
        mo.addAttribute("user", session.getAttribute("sessionUser"));
        return "user/detail";
    }

    @GetMapping("/user/update-form")
    public String updateForm(Model mo, HttpSession session){

        mo.addAttribute("user", session.getAttribute("sessionUser"));
        return "user/update-form";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO, HttpSession session){
        updateDTO.validate();

        User sessionUser = (User)session.getAttribute("sessionUser");
        User updatedUser = us.update(sessionUser.getId(), updateDTO, session);

        session.setAttribute("sessionUser", updatedUser);
        return "redirect:/user/detail";
    }

    @GetMapping("/join-form")
    public String joinForm(){
        return "user/join-form";
    }

    @PostMapping("/join")
    public String join(UserRequest.joinDTO joinDTO)  {
        joinDTO.validate();

        us.join(joinDTO);

        return "redirect:/login-form";
    }


}
