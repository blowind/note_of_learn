package com.zxf.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: FileUploadController
 * @Description:  文件上传相关处理
 * @Author: ZhangXuefeng
 * @Date: 2018/12/1 16:06
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/file")
public class FileUploadController {

    /*
    请求上传页面
    http://localhost:8080/file/page
    */
    @GetMapping("/page")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload/request")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request) {
        boolean flag = false;
        MultipartHttpServletRequest mreq = null;
        if(request instanceof MultipartHttpServletRequest) {
            mreq = (MultipartHttpServletRequest)request;
        }else{
            return dealResultMap(false, "上传失败");
        }

        MultipartFile mf = mreq.getFile("file");
        String fileName = mf.getOriginalFilename();
        File file = new File(fileName);
        try{
            mf.transferTo(file);
        }catch (Exception e) {
            e.printStackTrace();
            return dealResultMap(false, "上传失败2");
        }
        return dealResultMap(true, "上传成功");
    }

    @PostMapping("/upload/multipart")
    @ResponseBody
    public Map<String, Object> uploadMultipartFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File dest = new File(fileName);
        try{
            file.transferTo(dest);
        }catch (Exception e) {
            e.printStackTrace();
            return dealResultMap(false, "上传失败3");
        }
        return dealResultMap(true, "上传成功");
    }

    @PostMapping("/upload/part")
    @ResponseBody
    public Map<String, Object> uploadPart(Part file) {
        String fileName = file.getSubmittedFileName();
        try{
            file.write(fileName);
        }catch (Exception e) {
            e.printStackTrace();
            return dealResultMap(false, "上传失败4");
        }
        return dealResultMap(true, "上传成功");
    }

    private Map<String, Object> dealResultMap(boolean success, String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("msg", msg);
        return result;
    }
}
