package com.terry.blog.service.posts;

import com.terry.blog.domain.posts.Posts;
import com.terry.blog.domain.posts.PostsRepository;
import com.terry.blog.web.dto.PostsListResponseDto;
import com.terry.blog.web.dto.PostsResponseDto;
import com.terry.blog.web.dto.PostsSaveRequestDto;
import com.terry.blog.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));

        posts.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getKategorie());
        return id;
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id= "+id));
        postsRepository.delete(posts);
    }

    public PostsResponseDto findById (Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+ id));

        return new PostsResponseDto((entity));
    }

    @Transactional(readOnly = true)
    public Page<Posts> findAll(PageRequest pageRequest){
        return this.postsRepository.findAll(pageRequest);
    }

    @Transactional(readOnly =true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly =true)
    public List<PostsListResponseDto> hotviewList(){
        return postsRepository.hotviewList().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly =true)
    public Page<Posts> viewKategorie(String kategorie, PageRequest pageRequest){
        return postsRepository.viewKategorie(kategorie,pageRequest);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> searchPosts(String searchKeyword){
        return postsRepository.searchPosts(searchKeyword).stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void viewcount(Long id){
        postsRepository.viewCount(id);
    }
}
