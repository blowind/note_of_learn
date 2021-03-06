package com.zxf.spring.mybatis.dao;

import com.zxf.spring.mybatis.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/*使用@Repository注解主要是为了配合MapperScannerConfigurer方式装配所有Mapper接口，使用@Mapper注解也可以起一样的作用*/
@Repository
public interface UserMapper {
    User getUser(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    List<User> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(User record);
}