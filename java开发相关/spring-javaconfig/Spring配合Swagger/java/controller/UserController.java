package com.zxf.swagger.controller;

import com.zxf.swagger.model.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: UserController
 * @Description: Rest接口
 * @Author: ZhangXuefeng
 * @Date: 2018/11/20 14:21
 * @Version: 1.0
 **/
@Api("用户相关API")
@RestController
@RequestMapping("/user")
public class UserController {

    /*
     * 查看JSON格式的说明文档源文件 http://localhost:8080/v2/api-docs
     * 查看springfox-swagger-ui渲染过的说明文档页面 http://localhost:8080/swagger-ui.html
     *
     * 对于@ApiImplicitParam的paramType(不是必填参数，不确定的时候不填)：
     *      query、form域中的值需要使用@RequestParam获取，
     *      header域中的值需要使用@RequestHeader来获取，
     *      path域中的值需要使用@PathVariable来获取，
     *      body域中的值使用@RequestBody来获取，否则可能出错；
     *      而且如果paramType是body，name就不能是body，否则有问题，与官方文档中的“If paramType is "body", the name should be "body"不符。
     */
    @ApiOperation(value = "用户信息获取", notes = "根据id获取基本的信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true, defaultValue = "1", dataType = "Integer"),
                        @ApiImplicitParam(name = "param2", value = "展示占位用的变量", paramType = "query", dataType = "String")})
    @ApiResponse(code = 505, message = "自己定义的一个异常返回码")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public User index(@PathVariable("id") int id, String param2) {
        System.out.println(param2);
        User user = new User();
        user.setId(id);
        user.setName("kaikai");
        user.setNote("只是看看效果");
        return user;
    }
}
