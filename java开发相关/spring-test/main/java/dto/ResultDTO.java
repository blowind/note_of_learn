package com.springtest.dto;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/07/17 12:58
 */
public class ResultDTO<T> {
	private int code;
	private String msg;
	private T data;

	public ResultDTO() {
	}

	public ResultDTO(int code, String msg) {
		this(code, msg, null);
	}

	public ResultDTO(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}