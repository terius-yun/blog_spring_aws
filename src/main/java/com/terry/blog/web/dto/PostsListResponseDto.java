package com.terry.blog.web.dto;

import com.terry.blog.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private String kategorie;
    private LocalDateTime modifiedDate;
    private int viewCtn;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.kategorie = entity.getKategorie();
        this.modifiedDate = entity.getModifiedDate();
        this.viewCtn = entity.getViewCtn();
    }
}
