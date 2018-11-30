package com.zxf.springmvc.controller;

import com.zxf.springmvc.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName: ConverterController
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/30 16:00
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/converter")
public class ConverterController {

    /***
     * 使用自定义转换器，根据转换器中映射的类型进行转换（注意url中变量名与入参变量名一致）
     * http://localhost:8080/converter/converter?user=mytestname-mytestnote
     ***/

    @GetMapping("/converter")
    @ResponseBody
    public User convert(User user) {
        return user;
    }

    /***
     * 先使用GenericConverter划分数组，再使用自定义转换器转换每一个元素，根据转换器中映射的类型进行转换（注意url中变量名与入参变量名一致）
     * 下例进行三次字符串到对象的转换
     * http://localhost:8080/converter/converterListUser?userList=mytestname-mytestnote,mytest2name-mytest2note,hahah-hohoho
     ***/
    @GetMapping("/converterListUser")
    @ResponseBody
    public List<User> converter(List<User> userList) {
        return userList;
    }
}
