package com.zxf.spring.mybatis.handler;

import com.zxf.spring.mybatis.enums.SexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: SexTypeHandler
 * @Description: 性别枚举的映射处理器
 * @Author: ZhangXuefeng
 * @Date: 2018/11/19 22:57
 * @Version: 1.0
 **/
@MappedJdbcTypes(JdbcType.INTEGER)  /*声明JdbcType类型*/
@MappedTypes(value = SexEnum.class)  /*声明对应的JavaType*/
public class SexTypeHandler extends BaseTypeHandler<SexEnum> {
    /*通过列名读取性别*/
    @Override
    public SexEnum getNullableResult(ResultSet rs, String col) throws SQLException {
        int sex = rs.getInt(col);
        if(sex != 1 && sex != 2) {
            return null;
        }
        return SexEnum.getEnumById(sex);
    }

    /*通过下标读取性别*/
    @Override
    public SexEnum getNullableResult(ResultSet rs, int idx) throws SQLException {
        int sex = rs.getInt(idx);
        if(sex != 1 && sex != 2) {
            return null;
        }
        return SexEnum.getEnumById(sex);
    }

    /*通过存储过程读取性别*/
    @Override
    public SexEnum getNullableResult(CallableStatement cs, int idx) throws SQLException {
        int sex = cs.getInt(idx);
        if(sex != 1 && sex != 2) {
            return null;
        }
        return SexEnum.getEnumById(sex);
    }

    /*设置非空性别参数*/
    @Override
    public void setNonNullParameter(PreparedStatement ps, int idx, SexEnum sex, JdbcType jdbcType) throws SQLException {
        ps.setInt(idx, sex.getId());
    }

}
