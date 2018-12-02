package com.zxf.springmvc.PO;

import com.zxf.springmvc.enums.SexEnum;
import lombok.Data;

/**
 * @ClassName: UserPO
 * @Description: PO(Persistent Object)一般针对DAO层，直接对应数据库的表
 * @Author: ZhangXuefeng
 * @Date: 2018/12/2 23:45
 * @Version: 1.0
 **/
@Data
public class UserPO {
    private Long id;
    private String userName;
    private SexEnum sex;
    private String note;
}
