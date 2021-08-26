package com.terry.blog.web;

import com.terry.blog.service.util.S3Service;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ImageUploadController {
    private static final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);

    private S3Service s3Service = new S3Service();

    @PostMapping(value = "/api/v2/image/upload")
    public String ImageUpload(MultipartFile upload) throws Exception {
        System.out.println("upload = "+ upload);
        String imgPath = s3Service.upload(upload);

        return "{\"uploaded\": true, \"url\":\""+imgPath+"\"}";
    }
}
