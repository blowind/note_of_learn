package com.zxf.bootsecurity.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class SysRole {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
