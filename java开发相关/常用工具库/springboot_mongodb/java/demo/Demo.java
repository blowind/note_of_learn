package com.zxf.bootmongo.demo;

import com.zxf.bootmongo.model.Location;
import com.zxf.bootmongo.model.Person;
import com.zxf.bootmongo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Component
public class Demo implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    PersonRepository personRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        System.out.println("===================start====================");

        Person p = new Person("zxf", 18);
        Collection<Location> locations = new LinkedHashSet<>();
        Location loc1 = new Location("上海", "2001");
        Location loc2 = new Location("北京", "2002");
        Location loc3 = new Location("杭州", "2003");
        locations.add(loc1);
        locations.add(loc2);
        locations.add(loc3);
        p.setLocations(locations);
        Person retP = personRepository.save(p);
        System.out.println(retP);

        Person findP = personRepository.findByName(p.getName());
        System.out.println(findP);

        List<Person> queryP = personRepository.withQueryFindByAge(18);
        System.out.println(queryP);

        personRepository.deleteById(p.getId());
    }
}
