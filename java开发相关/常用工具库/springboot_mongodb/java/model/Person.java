package com.zxf.bootmongo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collection;
import java.util.LinkedHashSet;

@Document
@Getter
@Setter
@ToString
public class Person {
    @Id   // 表明本属性为文档ID
    private String id;
    private String name;
    private Integer age;
    @Field("locs")  // 注解次属性在文档中的名称为locs，
    private Collection<Location> locations = new LinkedHashSet<Location>();

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
