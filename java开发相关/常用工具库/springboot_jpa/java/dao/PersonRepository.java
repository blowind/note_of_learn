package com.zxf.bootdata.dao;

import com.zxf.bootdata.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByName(String name);
}
