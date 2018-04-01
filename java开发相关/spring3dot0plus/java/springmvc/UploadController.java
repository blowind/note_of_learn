package com.zxf;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

	/* 设置上传文件映射的方法，注意此处路径 /upload 对应的请求方法是POST，不涉及业务的GET方法在config里面直接映射跳转 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(MultipartFile file) {
		try{
			FileUtils.writeByteArrayToFile(new File("d:/upload/" + file.getOriginalFilename()), file.getBytes());
			return "ok";
		}catch (IOException e) {
			e.printStackTrace();
			return "wrong";
		}
	}
}
