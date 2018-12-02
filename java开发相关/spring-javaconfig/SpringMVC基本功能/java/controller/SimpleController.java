package com.zxf.springmvc.controller;

import com.zxf.springmvc.model.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SimpleController
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/29 22:14
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/simple")
public class SimpleController {

    /***
     无注解参数，参数名称必须与请求中的参数名完全一致，好处是允许参数为空
     http://localhost:8080/simple/paramNoAnnotation?intVal=11&longVal=2222
     ***/
    @GetMapping("/paramNoAnnotation")
    @ResponseBody
    public Map<String, Object> noAnnotation(Integer intVal, Long longVal, String str) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("intVal", intVal);
        paramsMap.put("longVal", longVal);
        paramsMap.put("str", str);
        return paramsMap;
    }

    /***
     注解参数，此时前端传入参数名可自行定制（由于注解里要使用其实做不到完全解耦），并且默认传入参数不能为NULL
     http://localhost:8080/simple/paramAnnotation?int_val=10&long_val=200
     ***/

    @GetMapping("/paramAnnotation")
    @ResponseBody
    public Map<String, Object> requestParam(@RequestParam("int_val") Integer intVal, @RequestParam("long_val") Long longVal, @RequestParam(value = "str_val", required = false) String str) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("intVal", intVal);
        paramsMap.put("longVal", longVal);
        paramsMap.put("str", str);
        return paramsMap;
    }

    /***
     传递数组，传入的数组参数内部使用逗号分隔
     http://localhost:8080/simple/requestArray?intArr=1,2,3&longArr=4,5,6&strArr=str1,str2,str3
     ***/
    @GetMapping("/requestArray")
    @ResponseBody
    public Map<String, Object> requestArray(int [] intArr, long[] longArr, String[] strArr) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("intArr", intArr);
        paramsMap.put("longArr", longArr);
        paramsMap.put("strArr", strArr);
        return paramsMap;
    }

    /***
     * 传递JSON，直接在入参映射成一个对象，多用于REST Ful接口
     * @RequestBody 邀请处理器用请求体的内容进行参数转换

     使用postman发送请求localhost:8080/simple/requestJSON
     在Header中设置Content-Type为application/json
     在Body中选择raw，内容填写{"userName":"wadaxi", "note":"just a test"}，注意此处一定要转成JSON字符串
     或者编写网页通过JQuery发送AJAX请求，其中关键为：
     var params = {
     userName: userName,
     note: note
     }
     $.post({
     url: "./requestJSON",
     contentType:"application/json",
     data: JSON.stringify(params),
     success: function(result) {}
     })***/
    @PostMapping("/requestJSON")
    @ResponseBody
    public User print(@RequestBody User user) {
        System.out.println(user);
        return user;
    }



    /***
     http://localhost:8080/simple/helloKitty
     通过URL传递参数，注意@GetMapping和@PathVariable中变量名要一致
     ***/
    @GetMapping("/{name}")
    @ResponseBody
    public User get(@PathVariable("name") String userName) {
        User user = new User();
        user.setUserName(userName);
        user.setNote("test path variable");
        return user;
    }

    /***
     获取格式化参数，
     使用postman发送请求localhost:8080/simple/formatData
     在Header中设置Content-Type为application/x-www-form-urlencoded
     在Body中选择application/x-www-form-urlencoded，内容填写两个字段
     date： 2018-10-10
     number： 1,234,567.89
     或者编写网页通过form表单提交
     ***/
    @PostMapping("/formatData")
    @ResponseBody
    public Map<String, Object> format(@DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date date, @NumberFormat(pattern = "#,###.##") Double number) {
        System.out.println("===========");
        System.out.println(number);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("date", date);
        dataMap.put("number", number);
        return dataMap;
    }

}
