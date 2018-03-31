package com.zxf.conditional;

public class LinuxListService implements ListService {
	@Override
	public String showListCmd() {
		return "ls";
	}
}
