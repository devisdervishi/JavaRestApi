package com.devis.java.spring.demo.service;

import com.devis.java.spring.demo.errors.PersonNotFoundException;
import com.devis.java.spring.demo.models.Person;
import com.devis.java.spring.demo.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {
    @Autowired
    PersonService service;
    @MockBean
    PersonRepository repo;
    @BeforeEach
    void setUp() {
        Person person=Person.builder().address("Tirana").id(1l).name("Devis").age(20).build();
        Mockito.when(repo.findById(1l)).thenReturn(Optional.of(person));
        Mockito.when(repo.findById(2l)).thenReturn(Optional.ofNullable(null));
    }

    @Test
    @DisplayName("Valid id returns valid Person")
    public void whenValidId_PersonShouldBeFound() throws PersonNotFoundException {
        Person testPerson=service.getPerson(1l);
        assertEquals(testPerson.getId(),1l);
        assertEquals(testPerson.getName(),"Devis");
        assertEquals(testPerson.getAddress(),"Tirana");
        assertEquals(testPerson.getAge(),20);
    }
    @Test
    @DisplayName("Invalid id throws exception")
    public void whenInvalidId_ExceptionThrown() throws PersonNotFoundException {

        Exception exception=assertThrows(PersonNotFoundException.class,()->{
            Person testPerson=service.getPerson(2l);
        });
        String excpectedMessage="Person with id: 2 doesnt exist";
        String actualMessage=exception.getMessage();
        assertEquals(excpectedMessage,actualMessage);
    }
}