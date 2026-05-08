package com.test.blog_spring.controller;


import com.test.blog_spring.dto.CommentRequest;
import com.test.blog_spring.entity.User;
import com.test.blog_spring.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService cs;

    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable Integer id, HttpSession session){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer boardId = cs.deleteById(id, sessionUser);
        return "redirect:/board/" + boardId;
    }

    @PostMapping("/reply/save")
    public String save(CommentRequest.SaveDTO saveDTO, HttpSession session){
        saveDTO.validate();

        User sessionUser = (User)session.getAttribute("sessionUser");

        cs.save(saveDTO, sessionUser);

        return "redirect:/board/" + saveDTO.getBoardId();
    }
}
