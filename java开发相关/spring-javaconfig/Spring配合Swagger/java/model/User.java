package com.zxf.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: User
 * @Description:
 *
 *  @ApiModel ��  �������ض�������壬���ڷ��ض�������
 *  @ApiModelProperty �� �������ԣ����ڳ������������ֶ���
 *
 * @Author: ZhangXuefeng
 * @Date: 2018/11/20 14:24
 * @Version: 1.0
 **/
@Data
@ApiModel("�����û�����")
public class User {
    /*���ڳ������������ֶ���*/
    @ApiModelProperty("�û�id")
    private int id;
    @ApiModelProperty("�û���")
    private String name;
    @ApiModelProperty("�û���ע")
    private String note;
}
