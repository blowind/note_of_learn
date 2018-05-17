package com.zxf.bootdata.dao;

import com.zxf.bootdata.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
	/***  crud基本功能不需要添加额外的接口，下面接口都是按需定制  ***/

	/**
	 * 如果POJO中定义了NamedQuery则使用定义的查询语句，否则根据方法名生成查询语句
	 * 通过名字相等查询相当于JPQL： select p from Person p where p.name=?1
	 * @param name
	 * @return
	 */
	List<Person> findByName(String name);

	/**
	 * 自己定义查询语句，覆盖根据方法名自动生成的查询语句
	 * 通过名字like查询，参数为name
	 * 相当于JPQL： select p from Person p where p.name like ?1
	 * @param name
	 * @return
	 */
	@Query("select p from Person p where p.name like ?1")
	List<Person> findByNameLike(String name);

	/**
	 * 使用名称而不是参数索引号匹配参数
	 * @param address
	 * @return
	 */
	@Query("select p from Person p where p.address= :address")
	List<Person> findByAddress(@Param("address") String address);

	/**
	 * 获得符合查询条件的前10条数据
	 * @param name
	 * @return
	 */
	List<Person> findFirst10ByName(String name);

	/**
	 * 获得符合查询条件的前30条数据
	 * @param name
	 * @return
	 */
	List<Person> findTop30ByName(String name);


	/**
	 * 更新查询，配合 @Modifying 注解，此处修改所有行，慎用
	 * @param name
	 * @return int 表示更新语句影响的行数
	 */
	@Modifying
	@Transactional
	@Query("update Person p set p.name=?1")
	int setName(String name);
}
