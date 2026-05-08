package com.test.blog_spring.service;

import com.test.blog_spring._core.errors.Exception400;
import com.test.blog_spring._core.errors.Exception401;
import com.test.blog_spring._core.errors.Exception404;
import com.test.blog_spring.dto.UserRequest;
import com.test.blog_spring.entity.User;
import com.test.blog_spring.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository ur;


    public User login(String username, String pwd){
        log.info("[로그인 시도] username: {}", username);

        User user = ur.findByIdAndPassword(username, pwd).orElseThrow(
                () -> new Exception401("로그인 정보가 올바르지 않습니다."));

        log.info("[로그인 성공] username: {}", user.getUsername());
        return user;
    }

    @Transactional
    public User update(Integer id, UserRequest.UpdateDTO updateDTO, HttpSession session){
        log.info("[정보 수정] ID : {}", id);
        User userEntity = ur.findById(id).orElseThrow(
                () -> new Exception404("회원 정보를 찾을 수 없습니다.")
        );

        userEntity.update(updateDTO);

        log.info("[정보 수정 성공] ID : {}", userEntity.getId());
        session.setAttribute("sessionUser", userEntity);
        return userEntity;
    }


    @Transactional
    public void join(UserRequest.joinDTO joinDTO) {
        log.info("[회원가입 요청] username : {}", joinDTO.getUsername());
        ur.findByUsername(joinDTO.getUsername()).ifPresent(user -> {
            log.warn("회원가입 실패 - 중복된 사용자명 : {}", user.getUsername());
            throw new Exception400("이미 존재하는 사용자명 입니다.");
        });

        User savedUser = ur.save(joinDTO.toEntity());
        log.info("[회원가입 성공] username : {}, ID : {}", savedUser.getUsername(), savedUser.getId());
    }
}
