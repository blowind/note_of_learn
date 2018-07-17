package com.springtest.controller;

import com.springtest.dto.ResultDTO;
import com.sun.xml.internal.ws.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 账户信息
 * @date 2018/07/17 12:55
 */
@Controller
@RequestMapping("/accounts")
public class AccountController {

	@RequestMapping("/{id}")
	@ResponseBody
	public ResultDTO<Map<String, Object>> getAccount(@PathVariable("id") int id) {
		if(id == 1) {
			Map<String, Object> data = new HashMap<>();
			data.put("moneyType", "BTC");
			return new ResultDTO<>(0, "is one", data);
		}else{
			return new ResultDTO<>(101, "not found");
		}
	}

	@RequestMapping(value = "/{id}/details", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<Map<String, Object>> getAccountDetail() {
		Map<String, Object> data = new HashMap<>();
		data.put("isDisplay", true);
		data.put("precise", 0.001);
		data.put("defaultMoneyType", "CNY");
		return new ResultDTO<>(0, "ok", data);
	}

	@RequestMapping(value = "/doc", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<Map<String, Object>> getFile(int tag, MultipartFile varName) {
		if(varName == null || varName.isEmpty()) {
			return new ResultDTO(101, "no file");
		}else{
			Map<String, Object> data = new HashMap<>();
			data.put("size", varName.getSize());
			return new ResultDTO<>(0, "file get", data);
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<Map<String, Object>> upload(@RequestParam(value = "data", required = false) MultipartFile file, int random) {
		if(file == null || file.isEmpty()) {
			return new ResultDTO<>(101, "no file");
		}else{
			Map<String, Object> data = new HashMap<>();
			data.put("tagName", file.getName());
			data.put("originFilename", file.getOriginalFilename());
			data.put("size", file.getSize());
			try{
				data.put("content", new String(file.getBytes(), "UTF-8"));
			}catch (IOException e) {
				e.printStackTrace();
			}

			return new ResultDTO<>(0, "file get", data);
		}
	}
}
