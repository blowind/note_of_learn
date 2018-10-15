package com.zxf.zxfbatis.simple.mapper;

import com.zxf.zxfbatis.simple.model.Country;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CountryMapper {
	List<Country> selectAll();

	/*注解的方式，直接将SQL写在里面，不推荐，XML有配同id内容会被覆盖*/
	@Select("select id, countryname, countrycode from country where id = 2")
	Country selectById2();

	void insertById(@Param("Country") Country country);
}
