package com.zxf.springmvc.controller;

import com.zxf.springmvc.PO.UserPO;
import com.zxf.springmvc.VO.UserVO;
import com.zxf.springmvc.enums.SexEnum;
import com.zxf.springmvc.exception.NotFoundException;
import com.zxf.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: RESTfulController
 * @Description: 展示REST ful接口的各项设计
 * @Author: ZhangXuefeng
 * @Date: 2018/12/2 23:39
 * @Version: 1.0
 **/
/*相当于给所有的方法加上了@ResponseBody注解，不显示指定ModelAndView的请求都用JSON返回
* 默认将方法都标注为application/json;charset=UTF-8*/
@RestController
public class RESTfulController {
    @Autowired
    private UserService userService;

    /*
    @GetMapping: 对应HTTP的GET请求，获取资源
    @PostMapping: 对应HTTP的POST请求，创建资源
    @PatchMapping: 对应HTTP的PATCH请求，提交资源部分修改的属性
    @PutMapping: 对应HTTP的PUT请求，提交所有资源属性以修改资源。
    @DeleteMapping: 对应HTTP的DELETE请求，删除服务端的资源
    这5中Mapping只是在@RequestMapping注解上进一步指定了method内容*/

    private UserPO changeToPO(UserVO userVO) {
        UserPO userPO = new UserPO();
        userPO.setId(userVO.getId());
        userPO.setUserName(userVO.getUserName());
        userPO.setSex(SexEnum.getSexEnum(userVO.getSexCode()));
        userPO.setNote(userVO.getNote());
        return userPO;
    }

    private UserVO changeToVO(UserPO userPO) {
        UserVO userVO = new UserVO();
        userVO.setId(userPO.getId());
        userVO.setUserName(userPO.getUserName());
        userVO.setSexCode(userPO.getSex().getCode());
        userVO.setSexName(userPO.getSex().getName());
        userVO.setNote(userPO.getNote());
        return userVO;
    }

    private List<UserVO> changeToVOes(List<UserPO> userPOList) {
        return userPOList.stream().map(this::changeToVO).collect(Collectors.toList());
    }

    /***
     * 使用postman发送请求http://localhost:8080/user
    在Header中设置Content-Type为application/json
    在Body中选择raw，内容填写
     {"userName": "hahaha", "sexCode": 1, "sexName": "male", "note": "jiluyixia"}
     和{"userName": "xixixi", "sexCode": 2, "sexName": "female", "note": "jixujilu"}，
     注意此处一定要转成JSON字符串*/
    /*创建一个用户
    * 使用ResponseEntity作为返回结果，可以指定具体的响应头和相应码*/
    @PostMapping("/user")
    public ResponseEntity<UserVO> insertUser(@RequestBody UserVO userVo) {
        UserPO userPO = this.changeToPO(userVo);
        UserVO ret = changeToVO(userService.insertUser(userPO));
        /*定义响应头*/
        HttpHeaders headers = new HttpHeaders();
        headers.add("success", "true");
        /*此处HttpStatus.CREATED状态码201表示创建资源成功*/
        return new ResponseEntity<UserVO>(ret, headers, HttpStatus.CREATED);
    }

    /*效果同上，使用@ResponseStatus注解而不是显示使用ResponseEntity返回结果*/
    @PostMapping("/user/add")
    @ResponseStatus(HttpStatus.CREATED)
    public UserVO insertUserAnnotation(@RequestBody UserVO userVo) {
        UserPO userPO = this.changeToPO(userVo);
        return changeToVO(userService.insertUser(userPO));
    }

    /*查询单个用户
    * http://localhost:8080/user/1
    * */
    @GetMapping("/user/{id}")
    /*配置HTTP请求的返回响应码，一般对返回OK 200来说不需显示指定，此处仅做展示*/
    @ResponseStatus(HttpStatus.OK)
    public UserVO getUser(@PathVariable("id") Long id) {
        UserPO userPO = userService.getUser(id);
        if(userPO == null) {
            /*此处抛出的异常会被@ControllerAdvice通知中指定的异常捕获器捕获*/
            throw new NotFoundException(101L, "找不到用户[" + id + "]的信息");
        }
        return changeToVO(userPO);
    }
    /*consumes限制本方法接收什么类型的的请求体，produces限定返回的媒体类型
    * 此处修改结果为普通文本类，则会被HttpMessageConverter的实现类StringHttpMessageConverter拦截处理，将结果转变为一个简单的字符串*/
    @GetMapping(value = "/user2/{id}",
            /*接收任何类型的请求体*/
            consumes = MediaType.ALL_VALUE,
            /*限定返回的媒体类型为文本*/
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String getUserName(@PathVariable("id") Long id) {
        UserPO userPO = userService.getUser(id);
        return userPO.getUserName();
    }

    /*查询符合要求的用户
    * http://localhost:8080/users/xixixi/jixujilu/0/1
    * 注意start和limit字段，哪怕不用也要填入，否则请求映射不到路径*/
    @GetMapping("/users/{userName}/{note}/{start}/{limit}")
    public List<UserVO> findUsers(@PathVariable("userName") String userName,
                                  @PathVariable("note") String note,
                                  @PathVariable("start") int start,
                                  @PathVariable("limit") int limit) {
        List<UserPO> userPOList = userService.findUsers(userName, note, start, limit);
        return this.changeToVOes(userPOList);
    }

    /*修改用户的全部信息*/
    /*HTTP PUT请求按照REST风格要求传递所有的属性
    * http://localhost:8080/user/1
    * 在Header中设置Content-Type为application/json
    * 在Body中选择raw，内容填写
    * {"userName": "hohoho", "sexCode": 1, "sexName": "male", "note": "biangengjilu"}
    * 注意此处一定要转成JSON字符串
    * */
    @PutMapping("/user/{id}")
    public UserVO updateUser(@PathVariable("id") Long id, @RequestBody UserVO userVO) {
        UserPO userPO = changeToPO(userVO);
        userPO.setId(id);
        userService.updateUser(userPO);
        return changeToVO(userPO);
    }

    /*修改用户的部分信息*/
    /*HTTP PATCH请求按照REST风格传递部分属性
    * http://localhost:8080/user/2/zhegemingzi
    * */
    @PatchMapping("/user/{id}/{userName}")
    public Map<String, Object> changeUserName(@PathVariable("id") Long id, @PathVariable("userName" ) String userName) {
        int result = userService.updateUserName(id, userName);
        Map<String, Object> ret = new HashMap<>();
        if(result > 0) {
            ret.put("msg", "更新用户名成功");
        }else{
            ret.put("msg", "更新用户名失败");
        }
        return ret;
    }

    /*删除用户
    http://localhost:8080/user/1
    */
    @DeleteMapping("/user/{id}")
    public Map<String, Object> deleteUser(@PathVariable("id") Long id) {
        int result = userService.deleteUser(id);
        Map<String, Object> ret = new HashMap<>();
        if(result > 0) {
            ret.put("msg", "删除成功");
        }else{
            ret.put("msg", "删除失败");
        }
        return ret;
    }

    /*指定返回ModelAndView后，SpringMVC将通过视图名找到对应视图，从而渲染模型，返回JSP页面*/
    @GetMapping("/user/jsp")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView(("restjsp"));
        return mv;
    }
}
