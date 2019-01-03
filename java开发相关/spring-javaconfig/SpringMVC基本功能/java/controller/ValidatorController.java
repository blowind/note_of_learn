package com.zxf.springmvc.controller;

import com.zxf.springmvc.model.User;
import com.zxf.springmvc.model.ValidatorPojo;
import com.zxf.springmvc.validator.UserValidator;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ValidatorController
 * @Description: 验证器相关的工具和自定义类的使用，所有要验证的参数都需要使用@Valid注解标注
 * @Author: ZhangXuefeng
 * @Date: 2018/11/30 15:40
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/validate")
public class ValidatorController {

    /***
     * 使用已有的验证机制
     * @Valid 注解表示启动验证机制进行验证，会自动将验证结果放入Errors对象中
     * 使用postman验证
     * http://localhost:8080/validate/validate
     * 在Header中设置Content-Type为application/json
    在Body中选择raw，内容
     * {
    "id":null,
    "date":"2015-10-10",
    "doubleVal":  98765.5454,
    "integer":100,
    "range":10000,
    "email":"email",
    "size":"adv12345"
    }
    验证结果使用JSON消息返回给前端
     ***/
    @PostMapping("/validate")
    @ResponseBody
    public Map<String, Object> validate(@Valid @RequestBody ValidatorPojo vp, Errors errors) {
        /*此处代码仅写了异常相关的处理，不涉及正常流程*/
        Map<String, Object> errMap = new HashMap<>();
        List<ObjectError> oes = errors.getAllErrors();
        for(ObjectError oe : oes) {
            String key = null;
            String msg = null;
            if(oe instanceof FieldError) {
                FieldError fe = (FieldError) oe;
                key = fe.getField();
            }else{
                key = oe.getObjectName();
            }
            msg = oe.getDefaultMessage();
            errMap.put(key, msg);
        }
        return errMap;
    }

    /***
     * 使用自定义的验证机制
     * 使用@InitBinder注解的方法在执行本控制器方法前被执行，此时绑定自定义验证器，作用在本控制的所有相关方法上
     * 此处额外设置日期格式的验证，表明所有入参类型为Date的参数，都按照指定格式转换,效果等价于使用@DateTimeFormat注解定义日志格式化
     *
     * Spring MVC中对于requestBody中发送的数据转换不是通过databind来实现，而是使用HttpMessageConverter来实现具体的类型转换。
     * 因此此处initBinder仅对get类请求生效
     * */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserValidator());
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }

    /***
     * 验证是否使用自定义的验证器UserValidator验证了传入入参User
        http://localhost:8080/validate/customValidate?user=-hereisnote&date=2011-10-19
     */

    @GetMapping("/customValidate")
    @ResponseBody
    public Map<String, Object> customValidate(@Valid User user, Errors errors, Date date) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("date", date);
        if(errors.hasErrors()) {
            List<ObjectError> oes = errors.getAllErrors();
            for(ObjectError oe: oes) {
                if(oe instanceof FieldError) {
                    FieldError fe = (FieldError)oe;
                    map.put(fe.getField(), fe.getDefaultMessage());
                }else{
                    map.put(oe.getObjectName(), oe.getDefaultMessage());
                }
            }
        }
        return map;
    }

}
