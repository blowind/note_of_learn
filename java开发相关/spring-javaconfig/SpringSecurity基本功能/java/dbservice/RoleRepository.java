package com.zxf.spring.security.dbservice;

import com.zxf.spring.security.model.DatabaseRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *  @InterfaceName: RoleRepository
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/13 17:38
 *  @Version: 1.0
 **/
public interface RoleRepository extends JpaRepository<DatabaseRole, Integer> {

	/*注意到此处SQL里面from后面不是跟着表，而是Entity实体类，后面的字段也是属性名而不是数据库表的字段名*/
	@Query("select r from DatabaseUser u, DatabaseUserRole ur, DatabaseRole r "
			+ "where u.id = ur.userId and r.id = ur.roleId and u.userName = ?1 ")
	List<DatabaseRole> findByUsername(String username);
}
