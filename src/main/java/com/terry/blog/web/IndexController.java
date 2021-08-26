package com.terry.blog.web;

import com.terry.blog.config.auth.LoginUser;
import com.terry.blog.config.auth.dto.SessionUser;
import com.terry.blog.service.posts.PostsService;
import com.terry.blog.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;


    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

    @GetMapping("/posts/search")
    public String searchPosts(Model model, @LoginUser SessionUser user, @RequestParam("serachKeyword") String keyword){
        model.addAttribute("posts",postsService.searchPosts(keyword));
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
}
