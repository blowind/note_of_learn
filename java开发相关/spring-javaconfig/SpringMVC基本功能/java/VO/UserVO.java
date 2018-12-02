package com.zxf.springmvc.VO;

import lombok.Data;

/**
 * @ClassName: UserVO
 * @Description: VO(View Object)视图对象，主要是与前端交互信息使用
 * @Author: ZhangXuefeng
 * @Date: 2018/12/2 23:51
 * @Version: 1.0
 **/
@Data
public class UserVO {
    private Long id;
    private String userName;
    /*枚举类型对前端来说是难以处理的，因此在VO中转换协商一致的基本类型数据*/
    private int sexCode;
    private String sexName;
    private String note;
}
