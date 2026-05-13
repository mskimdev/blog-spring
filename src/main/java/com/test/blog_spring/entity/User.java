package com.test.blog_spring.entity;

import com.test.blog_spring.dto.UserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Table(name = "user_tb")
@Entity
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @CreationTimestamp
    private Timestamp createdAt;

    // User 테이블에는 이미지 파일명만 저장할 예정 (실제 데이터는 서버 컴퓨터 로컬에 저장할 예정)
    // 프로필 이미지는 회원가입시 선택사항 (nullable)
    private String profileImg;


    public void validate() {
        if(username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("사용자명을 입력하세요");
        }

        if(password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력하세요");
        }
    }

    // User Update
    public void update(UserRequest.UpdateDTO updateDTO, String newProfileImg) {

        this.password = updateDTO.getPassword();
        this.profileImg = newProfileImg;
    }
}
