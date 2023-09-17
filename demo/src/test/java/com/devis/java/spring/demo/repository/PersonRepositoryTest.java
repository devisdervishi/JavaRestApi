package com.devis.java.spring.demo.repository;

import com.devis.java.spring.demo.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {
@Autowired
PersonRepository repository;
@Autowired
TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Person person=Person.builder().name("Devis").age(15).address("Tirana").build();
        entityManager.persist(person);
    }

    @Test
    @DisplayName("Repository returns right person when a specified id is given")
    void whenGetPersonById_thenRightValueReturned(){
    Person tmp=repository.getById(1L);
    assertEquals(tmp.getName(),"Devis");
    }
}