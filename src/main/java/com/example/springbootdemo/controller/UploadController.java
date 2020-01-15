package com.example.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/14.
 */
@Controller
@RequestMapping("/file/")
public class UploadController {
    @RequestMapping("/file")
    public String upload(){
        return "upload";
    }
    @RequestMapping("upload")
    public String upload(HttpServletRequest request,HttpServletResponse response) throws IOException {
//      创建一个通用的多部分解析器
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//      判断request是否有文件上传，即多部分解析器
        if(commonsMultipartResolver.isMultipart(request)){
//      转换成多部分request
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//      取得request中所有文件名
            Iterator<String> iter = multipartHttpServletRequest.getFileNames();
            while (iter.hasNext()) {
//      取得上传文件
                MultipartFile file = multipartHttpServletRequest.getFile((String) iter.next());
//      对上传文件进行处理（多文件和单文件上传这部分是通用的）
                if (file != null) {
                    String fileName = System.currentTimeMillis() + file.getOriginalFilename();
                    System.out.print(fileName);
                    String path = "E:/" + fileName;
                    File localFile = new File(path);
                    file.transferTo(localFile);
                }
            }
        }
        return  "success";
    }
}
