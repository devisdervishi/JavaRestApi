package com.devis.java.spring.demo.repository;

import com.devis.java.spring.demo.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    //Demonstration of fetching data using jpql query
    @Query(value = "select p from Person p where p.age=?1 ")
    List<Person> findPersonsWithSpecifiedAge(int age);
//Demonstration of fetching data using native sql query
    @Query(value = "select * from Person where age>?1",nativeQuery = true)
    List<Person> findPersonsWithBiggerAgeThanSpecifiedAge(int age);

    @Query(
            value = "select * from Person as p where p.name=:p_name",
            nativeQuery = true
    )
    List<Person> findAllPersonsWithSpecifiedName(@Param("p_name") String name);
}


