package com.zxf.springmvc.controller;

import com.zxf.springmvc.PO.UserPO;
import com.zxf.springmvc.VO.UserVO;
import com.zxf.springmvc.enums.SexEnum;
import com.zxf.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: RESTfulController
 * @Description: 展示REST ful接口的各项设计
 * @Author: ZhangXuefeng
 * @Date: 2018/12/2 23:39
 * @Version: 1.0
 **/
/*相当于给所有的方法加上了@ResponseBody注解，不显示指定ModelAndView的请求都用JSON返回*/
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

    /*创建一个用户*/
    @PostMapping("/user")
    public UserVO insertUser(@RequestBody UserVO userVo) {
        UserPO userPO = this.changeToPO(userVo);
        return changeToVO(userService.insertUser(userPO));
    }

    /*查询单个用户*/
    @GetMapping("/user/{id}")
    public UserVO getUser(@PathVariable("id") Long id) {
        UserPO userPO = userService.getUser(id);
        return changeToVO(userPO);
    }

    /*查询符合要求的用户*/
    @GetMapping("/users/{userName}/{note}/{start}/{limit}")
    public List<UserVO> findUsers(@PathVariable("userName") String userName,
                                  @PathVariable("note") String note,
                                  @PathVariable("start") int start,
                                  @PathVariable("limit") int limit) {
        List<UserPO> userPOList = userService.findUsers(userName, note, start, limit);
        return this.changeToVOes(userPOList);
    }

    /*修改用户的全部信息*/
    /*HTTP PUT请求按照REST风格要求传递所有的属性*/
    @PutMapping("/user/{id}")
    public UserVO updateUser(@PathVariable("id") Long id, @RequestBody UserVO userVO) {
        UserPO userPO = changeToPO(userVO);
        userPO.setId(id);
        userService.updateUser(userPO);
        return changeToVO(userPO);
    }

    @PatchMapping("/user/{id}/{userName}")
    public UserVO changeUserName(@PathVariable("id") Long id, @PathVariable("userName" ) String userName) {
        int result = userService.updateUserName(id, userName);
    }
}
