package com.test.blog_spring.controller;

import com.test.blog_spring.dto.BoardRequest;
import com.test.blog_spring.dto.BoardResponse;
import com.test.blog_spring.entity.Board;
import com.test.blog_spring.entity.User;
import com.test.blog_spring.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService bs;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/board/list")
    public String boardList(Model mo){

        mo.addAttribute("boardList", bs.findAllJoinUser());
        return "board/list";
    }

    @GetMapping("/board/save-form")
    public String saveForm(){
        return "board/save-form";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO, HttpSession session){
        saveDTO.validate();

        bs.save(saveDTO, (User)session.getAttribute("sessionUser"));

        return "redirect:/board/list";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Integer id, Model mo, HttpSession session){
        BoardResponse.DetailDTO board = bs.detail(id);
        mo.addAttribute("board", board);
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser != null && sessionUser.getId().equals(board.getUserId())) {
            mo.addAttribute("isOwner", true);
        }
        return "board/detail";
    }

    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable Integer id, Model mo, HttpSession session){
        User sessionUser = (User)session.getAttribute("sessionUser");
        BoardResponse.DetailDTO detailDTO = bs.updatePage(id, sessionUser);
        mo.addAttribute("board", detailDTO);
        return "/board/update-form";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO updateDTO, HttpSession session){
        updateDTO.validate();
        User sessionUser = (User)session.getAttribute("sessionUser");
        bs.update(id, updateDTO, sessionUser);

        return "redirect:/board/list";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id, HttpSession session){
        bs.delete(id, (User)session.getAttribute("sessionUser"));
        return "redirect:/board/list";
    }


}
