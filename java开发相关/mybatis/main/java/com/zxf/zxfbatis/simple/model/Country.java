package com.zxf.zxfbatis.simple.model;

import org.apache.ibatis.type.Alias;

/* mapper xml文件中使用的别名，用于默认别名发生同名冲突时指定具体的别名 */
@Alias("country")
public class Country {
	private Long id;
	private String countryname;
	private String countrycode;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getCountryname() {
		return countryname;
	}

	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

}
