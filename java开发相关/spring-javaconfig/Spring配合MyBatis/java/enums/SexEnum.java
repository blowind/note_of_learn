package com.zxf.spring.mybatis.enums;

public enum SexEnum {
    MALE(1),
    FEMALE(2);

    private Integer id;

    SexEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static SexEnum getEnumById(Integer id) {
        for(SexEnum e : SexEnum.values()) {
            if(e.getId() == id) {
                return e;
            }
        }
        return null;
    }
}
