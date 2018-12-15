package com.zxf.spring.webflux.enums;

public enum SexEnum {
	MALE(1, "男"),
	FEMALE(2, "女");

	private int code;
	private String name;

	SexEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static SexEnum getSexEnum(int code) {
		SexEnum[] enums = SexEnum.values();
		for(SexEnum e : enums) {
			if(e.getCode() == code) {
				return e;
			}
		}
		return null;
	}
}
