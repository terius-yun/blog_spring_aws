package com.terry.blog.domain.posts;

import com.terry.blog.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
        private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private String kategorie;

    private int viewCtn = 0;

    @Builder
    public Posts(String title, String content, String author,String kategorie , int viewCtn){
        this.title = title;
        this.content = content;
        this.author = author;
        this.kategorie = kategorie;
        this.viewCtn = viewCtn;
    }

    public void update(String title, String content, String kategorie){
        this.title = title;
        this.content = content;
        this.kategorie = kategorie;
    }
}
