package com.terry.blog.web;

import com.terry.blog.config.auth.LoginUser;
import com.terry.blog.config.auth.dto.SessionUser;
import com.terry.blog.domain.posts.Posts;
import com.terry.blog.domain.posts.PostsRepository;
import com.terry.blog.service.posts.PostsService;
import com.terry.blog.web.dto.PagingCountDto;
import com.terry.blog.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private final PostsService postsService;
    private final PostsRepository postsRepository;
    private final HttpSession httpSession;

    //메인화면
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        //인기게시글 불러오기
        model.addAttribute("posts", postsService.hotviewList());
        if(user != null){//권한확인 후 권한이없다면 뷰로 있다면 수정으로
            if(user.getRole().toString().equals("USER")){
                model.addAttribute("hasRole", "true");
            }
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
    //카테고리 선택
    @GetMapping("/view/{kategorie}")
    public String kategoriePosts(Model model, @LoginUser SessionUser user, @PathVariable String kategorie,
                                 @RequestParam("page") int page){
        PageRequest pageRequest = PageRequest.of(page-1,5);
        if(kategorie.equals("ALL")){//전체카테고리선택
//            model.addAttribute("posts",postsService.findAllDesc());
            model.addAttribute("posts",postsService.findAll(pageRequest));//전체 게시글 및 페이징 가져오기

            PagingCountDto pagingCountDto = new PagingCountDto(postsService.findAll(pageRequest).getTotalPages());//페이징 갯수가져오기
            model.addAttribute("pages",pagingCountDto.getPages());
        }else {// 상세 카테고리선택시
            model.addAttribute("posts",postsService.viewKategorie(kategorie, pageRequest));

            PagingCountDto pagingCountDto = new PagingCountDto(postsService.viewKategorie(kategorie, pageRequest).getTotalPages());
            model.addAttribute("pages",pagingCountDto.getPages());
        }
        model.addAttribute("kategories",kategorie);
        if(user != null){//인가확인
            if(user.getRole().toString().equals("USER")){
                model.addAttribute("hasRole", "true");
            }
            model.addAttribute("userName", user.getName());
        }
        return "kategoriePosts-view";
    }

    //글등록뷰
    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "posts-save";
    }
    //수정뷰
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        if(user != null){
            model.addAttribute("userName", user.getName());
        }

        return "posts-update";
    }
    //게시글 보기
    @GetMapping("/viewPost/{id}")
    public String viewPosts(@PathVariable Long id, Model model, @LoginUser SessionUser user){
        postsService.viewcount(id);
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        if(user != null){//권한확인 후 권한이없다면 뷰로 있다면 수정으로
            if(user.getRole().toString().equals("USER")){
                model.addAttribute("email", user.getEmail().toString());
                model.addAttribute("hasRole", "true");
            }
            model.addAttribute("userName", user.getName());
        }

        return "posts-view";
    }
    //검색로직
    @GetMapping("/view/search")
    public String searchPosts(Model model, @LoginUser SessionUser user, @RequestParam("serachKeyword") String keyword){
        model.addAttribute("posts",postsService.searchPosts(keyword));
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
}
