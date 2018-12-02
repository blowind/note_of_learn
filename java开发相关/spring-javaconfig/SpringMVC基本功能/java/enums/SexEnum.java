package com.zxf.springmvc.enums;

public enum SexEnum {
    MALE(1, "male"),
    FEMALE(2, "female");

    private int code;
    private String name;

    private SexEnum(int code, String name) {
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
        for(SexEnum e : SexEnum.values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }
}
