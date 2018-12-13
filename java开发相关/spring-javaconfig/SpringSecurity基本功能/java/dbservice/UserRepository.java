package com.zxf.spring.security.dbservice;

import com.zxf.spring.security.model.DatabaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *  @ClassName: UserRepository
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/13 17:31
 *  @Version: 1.0
 **/
public interface UserRepository extends JpaRepository<DatabaseUser, Integer> {

	@Query("select u from DatabaseUser u where u.userName = ?1")
	List<DatabaseUser> findByUserName(String username);

}