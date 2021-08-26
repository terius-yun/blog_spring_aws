package com.terry.blog.web;

import com.terry.blog.service.posts.PostsService;
import com.terry.blog.service.util.ImageUploader;
import com.terry.blog.service.util.S3Service;
import com.terry.blog.web.dto.PostsResponseDto;
import com.terry.blog.web.dto.PostsSaveRequestDto;
import com.terry.blog.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return  postsService.update(id,requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }


//    @PostMapping(value = "/api/v1/image/upload")
//    public String ImageUpload(HttpServletRequest request,
//                              HttpServletResponse response,MultipartFile upload) throws Exception{
////        System.out.println("upload = "+ upload);
////        String imgPath = s3Service.upload(upload);
////        return imgPath;
//                    ImageUploader uploader = new ImageUploader();
//        return uploader.ImageUploader(request,response,upload);
//    }
}

