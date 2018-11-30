package com.zxf.springmvc.converter;

import com.zxf.springmvc.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 * @ClassName: StringToUserConverter
 * @Description: 此处指定所有字符串到User对象的转换都使用本转换器
 * @Author: ZhangXuefeng
 * @Date: 2018/11/30 14:11
 * @Version: 1.0
 **/
/*
主要有Converter,Formatter,GenericConverter三类转换接口将前端HTTP请求中的数据转换为controller中的入参
1、Converter 一对一的普通转换器，根据controller中的入参类型将HTTP请求中的内容转换为相应对象
2、Formatter 格式化器，例如将日期字符串按照约定的格式转换为日期，用的较少
3、GenericConverter 将HTTP参数转换为数组
*/
@Component    /*一定要作为Bean注册到Spring容器*/
public class StringToUserConverter implements Converter<String, User> {

    @Override
    public User convert(String userStr) {
        System.out.println("调用自定义转换器将普通字符串按格式要求转换为User对象");
        User user = new User();
        String[] strArr = userStr.split("-");
        String userName = strArr[0];
        String note = strArr[1];
        user.setUserName(userName);
        user.setNote(note);
        return user;
    }
}
