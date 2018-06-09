package com.zxf.bootsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Msg {
    private String title;
    private String content;
    private String etraInfo;
}
