package com.zxf.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.zxf.springmvc.util.Base64Util.gzipCompress;
import static com.zxf.springmvc.util.Base64Util.gzipUncompress;
import static com.zxf.springmvc.util.Base64Util.loadFile;

/**
 * @ClassName: FileUploadController
 * @Description:  文件上传相关处理，依赖Servlet API提供的StandardServletMultipartResolver
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

    /*使用HttpServletRequest作为入参*/
    @PostMapping("/upload/request")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request) {
        boolean flag = false;
        MultipartHttpServletRequest mreq = null;
        /*强制转换为MultipartHttpServletRequest接口类型后才能使用相关方法(具体的类也要求是这种类型)*/
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

    /*使用Spring MVC的MultipartFile类作为参数*/
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

    /*使用Servlet的API Part作为参数*/
    @PostMapping("/upload/part")
    @ResponseBody
    public Map<String, Object> uploadPart(Part file) {
        /*获取提交文件名称*/
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

    @GetMapping("/download")
    public String getButtonPage() {
        return "download";
    }

    /**
     * 返回json格式的二进制文件（通过BASE64编码二进制文件）
     */
    @GetMapping("/picture")
    @ResponseBody
    public Map<String, Object> getPicture() {
        try{
            String filePath = "E:\\crisis_intervention.jpg";
            String filename = filePath.substring(filePath.lastIndexOf("\\") + 1);
            String fileInBASE64 = new String(Base64.getMimeEncoder().encode(loadFile(filePath)), "utf-8");

            Map<String, Object> ret = new HashMap<>();
            ret.put("code", 0);
            ret.put("filename", filename);
            ret.put("fileInBase64", fileInBASE64);
            return ret;
        }catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        return null;
    }

    @GetMapping("/txt")
    @ResponseBody
    public Map<String, Object> getTxt() {
        try{
            String filePath = "E:\\abc.txt";
            String filename = filePath.substring(filePath.lastIndexOf("\\") + 1);
            String fileInBASE64 = new String(Base64.getMimeEncoder().encode(loadFile(filePath)), "utf-8");

            Map<String, Object> ret = new HashMap<>();
            ret.put("code", 0);
            ret.put("filename", filename);
            ret.put("fileInBase64", fileInBASE64);
            return ret;
        }catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        return null;
    }

    /**
     * 一般来说BASE64编码是为了任意编码的内容转成ASCII码字符便于网络传输，
     * 因此gzip压缩一定在Base64编码之前，否则gzip压缩后可能又破坏了Base64的成果
     */
    @GetMapping("/string")
    @ResponseBody
    public Map<String, Object> getString() {
        try{
            String text = new String(Base64.getMimeEncoder().encode(gzipCompress("发送的新内容".getBytes())), "utf-8");
            Map<String, Object> ret = new HashMap<>();
            ret.put("code", 0);
            ret.put("text", text);
            return ret;
        }catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        return null;
    }

    @PostMapping("/gzipData")
    @ResponseBody
    public String getGzipData(HttpServletRequest request, HttpServletResponse res) {
        try{
//            String contentEncoding = request.getHeader("Content-Encoding");
//            if(contentEncoding != null && contentEncoding.equals("customedGzip")) {
                BufferedReader reader = request.getReader();
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                byte[] bytes = sb.toString().getBytes("UTF-8");
                String params = new String(gzipUncompress(bytes), "UTF-8");
                System.out.println(params);
                return "ok";
//            }
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return "error";
    }
}
