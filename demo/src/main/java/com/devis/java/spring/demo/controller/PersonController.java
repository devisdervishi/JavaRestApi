package com.devis.java.spring.demo.controller;

import com.devis.java.spring.demo.errors.PersonNotFoundException;
import com.devis.java.spring.demo.models.Person;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.devis.java.spring.demo.service.PersonService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PersonController {

    @Autowired
    private PersonService service;
    private final Logger LOGGER= LoggerFactory.getLogger(PersonController.class);

    @RequestMapping("/")
    public String hello(){
        return "Hello";
    }
    @PostMapping("/person")
    public Person savePerson(@Valid @RequestBody Person person){
        LOGGER.info("Person controller: savePerson : reqBody param:"+person+"\n");

       return service.savePerson(person);
    }
    @GetMapping("/person/{id}")
    public Person getPersonById(@PathVariable("id") Long id) throws PersonNotFoundException {
        return service.getPerson(id);

    }

    @GetMapping("/persons/{age}")
    public List<Person> getPersonsBySpecificAge(@PathVariable("age") int age){
        return service.getPersonsByCertainAge(age);
    }
    @GetMapping("/persons>/{age}")
    public List<Person> getPersonsWithBiggerAgeThanSpecifiedAge(@PathVariable("age") int age){
        return service.getPersonsWithBiggerAgeThanSpecifiedAge(age);
    }
    @GetMapping("/persons")
    public List<Person> getAllPersons(){
        return service.getAllPersons();
    }

    @PutMapping("person/{id}")
        public Person updatePerson(@PathVariable("id") Long id,@RequestBody Person updatedPerson) throws PersonNotFoundException {
            return service.updatePerson(id, updatedPerson);
        }

        @DeleteMapping("person/{id}")
        public String deletePersonByID(@PathVariable("id") Long id) throws PersonNotFoundException {
            return service.deletePerson(id);
        }
}
