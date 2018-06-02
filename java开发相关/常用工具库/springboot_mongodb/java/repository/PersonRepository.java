package com.zxf.bootmongo.repository;

import com.zxf.bootmongo.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonRepository extends MongoRepository<Person, String> {

    /*  方法名查询 */
    Person findByName(String name);

    /*  @Query查询，查询参数要构造成JSON字符串 */
    @Query("{'age': ?0}")
    List<Person> withQueryFindByAge(Integer age);

    void deleteById(String id);
}
