package com.terry.blog.service.util;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

public class ImageUploader {
    public static String ImageUploader(HttpServletRequest request,
                                HttpServletResponse response,
                                MultipartFile upload){
//        File fileDir = new File("D:/intellij_project/blog_spring_aws/src/main/resources/static/uploadImages/");
        File fileDir = new File(request.getSession().getServletContext().getRealPath("/"));
        System.out.println(request.getSession().getServletContext().getContext("/").getRealPath(""));


        UUID uid = UUID.randomUUID();
        OutputStream out = null;
        PrintWriter printWriter = null;
        String ckUploadPath = null;

        try {


            response.setCharacterEncoding("utf-8");
            response.setContentType("apllication/json; charset=utf-8");

            //파일이름 가져오기
            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();

            //이미지 경로 생성
            String path = fileDir.getPath();
            ckUploadPath = path + "\\" + fileName;
            System.out.println(ckUploadPath);
            File folder = new File(path);

            //해당 디렉토리 확인
            if(!folder.exists()){
                try{
                    folder.mkdirs(); // 폴더 생성
                }catch(Exception e){
                    e.getStackTrace();
                }
            }

            out = new FileOutputStream(new File(ckUploadPath));
            out.write(bytes);
            out.flush(); // outputStram에 저장된 데이터를 전송하고 초기화
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(out != null) { out.close(); }
                if(printWriter != null) { printWriter.close(); }
            } catch(IOException e) { e.printStackTrace(); }
        }
        String returned = "{\"uploaded\": true, \"url\":\"http://localhost:9090/uploadImages/"+upload.getOriginalFilename()+"\"}";

        return returned;
    }
}
