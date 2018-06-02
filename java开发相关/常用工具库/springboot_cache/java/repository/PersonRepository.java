package com.zxf.springcache.repository;

import com.zxf.springcache.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 接口
 * @date 2018/06/02 11:50
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
