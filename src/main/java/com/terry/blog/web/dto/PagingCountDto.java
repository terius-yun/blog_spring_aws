package com.terry.blog.web.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PagingCountDto {
    private int totalPage;

    public PagingCountDto( int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Integer> getPages(){
        ArrayList pages =new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            pages.add(i+1);
        }
        return pages;
    }
}
