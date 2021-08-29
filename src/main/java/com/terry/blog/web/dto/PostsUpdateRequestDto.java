package com.terry.blog.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;
    private String kategorie;

    @Builder
    public PostsUpdateRequestDto(String title, String content, String kategorie){
        this.title = title;
        this.content = content;
        this.kategorie = kategorie;
    }
}
