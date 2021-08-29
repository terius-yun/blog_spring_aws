package com.terry.blog.web.dto;

import com.terry.blog.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String kategorie;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, String kategorie){
        this.title = title;
        this.content = content;
        this.author = author;
        this.kategorie = kategorie;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .kategorie(kategorie)
                .build();
    }
}
