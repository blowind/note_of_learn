package com.zxf.bootdata.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@Entity
@NamedQuery(name = "Person.findByName", query = "select p from Person p where p.name=?1")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;
	private Integer age;
	private String address;

	@Column(name = "create_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	public Date createTime;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "project_id")
//	public Project project;

	public Person(String name, Integer age, String address, Date date) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.createTime = date;
	}
}
