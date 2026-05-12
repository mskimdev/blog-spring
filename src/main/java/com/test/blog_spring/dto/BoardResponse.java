package com.test.blog_spring.dto;

import com.test.blog_spring.entity.Board;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

    @Data
    public static class ListDTO {
        private Integer id;
        private String title;
        private String content;
        private String username;
        private String createdAt;

        public ListDTO(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.username = board.getUser().getUsername();
            this.createdAt = board.getTime();
        }
    }

    // 페이징 DTO 설계
    // Page<Board> page를 우리 사용할 DTO 클래스로 단순히 변환
    @Data
    public static class PageDTO{
        private List<ListDTO> list;
        private int currentPage;
        private int size;
        private int totalPages;
        private long totalElements;
        private boolean first;
        private boolean last;
        private int prevPage;
        private int nextPage;

        private List<PageItem> pageItemNumbers;


        public PageDTO(Page<Board> page){
            this.list = page.getContent().stream().map(ListDTO::new).toList();
            // 사전 지식 : page.getNumber() <-- 0번부터 시작
            this.currentPage = page.getNumber() + 1;
            this.size = page.getSize();
            this.totalPages = page.getTotalPages();
            this.totalElements = page.getTotalElements();
            this.first = page.isFirst();
            this.last = page.isLast();
            // 이전, 다음 페이지 번호
            this.prevPage = this.first ? this.currentPage : this.currentPage - 1;
            this.nextPage = this.last ? this.currentPage : this.currentPage + 1;

            int start = Math.max(1, this.currentPage - 2);
            int end = Math.min(this.totalPages, this.currentPage + 2);

            this.pageItemNumbers = new ArrayList<>();

            for(int i = start; i <= end; i++){
                this.pageItemNumbers.add(new PageItem(i, i == this.currentPage));
            }

        }


    } // end of PageDTO

    @Data
    public static class PageItem{
        private int number;
        private boolean active;

        public PageItem(int number, boolean active) {
            this.number = number;
            this.active = active;
        }
    }

    @Data
    public static class DetailDTO{
        private Integer id; // board PK
        private Integer userId; // user PK
        private String title;
        private String content;
        private String username;
        private String createdAt;

        public DetailDTO(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            if(board.getUser() != null) {
                this.username = board.getUser().getUsername();
                this.userId = board.getUser().getId();
            }
            this.createdAt = board.getTime();
        }
    }
}
