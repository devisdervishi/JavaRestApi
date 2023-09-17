package com.devis.java.spring.demo.service;

import com.devis.java.spring.demo.errors.PersonNotFoundException;
import com.devis.java.spring.demo.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import com.devis.java.spring.demo.repository.PersonRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repo;


    public Person savePerson(Person person){
        return repo.save(person);

    }
    public Person getPerson(Long id) throws PersonNotFoundException {
        Optional<Person> person= repo.findById(id);
        if (person.isPresent()){
            return person.get();
        }
        else throw new PersonNotFoundException("Person with id: "+id+" doesnt exist");
    }

    public List<Person> getPersonsByCertainAge(int age){
        return repo.findPersonsWithSpecifiedAge(age);
    }
    public List<Person> getPersonsWithBiggerAgeThanSpecifiedAge(int age){
        return repo.findPersonsWithBiggerAgeThanSpecifiedAge(age);
    }

    public List<Person> getAllPersons(){
        return repo.findAll();
    }

    @Transactional
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    public Person updatePerson(Long id,Person updatedPerson) throws PersonNotFoundException {
        Optional<Person> personToBeUpdated=repo.findById(id);
        if (personToBeUpdated.isPresent()){
            if (Objects.nonNull(updatedPerson.getName())&&!updatedPerson.getName().equals(personToBeUpdated.get().getName())){
                personToBeUpdated.get().setName(updatedPerson.getName());
            }
            if (Objects.nonNull(updatedPerson.getAddress())&&!updatedPerson.getAddress().equals(personToBeUpdated.get().getAddress())){
                personToBeUpdated.get().setAddress(updatedPerson.getAddress());
            }
            Integer updatetPersonAge=updatedPerson.getAge();
            if (updatetPersonAge!=null&&updatetPersonAge!=personToBeUpdated.get().getAge()){
                personToBeUpdated.get().setAge(updatedPerson.getAge());
            }
            return repo.save(personToBeUpdated.get());
        }
        else throw new PersonNotFoundException("Person with id: "+id+" doesnt exist");
    }

    @Transactional
    @Modifying(flushAutomatically = true,clearAutomatically = true)
    public String deletePerson(Long id) throws PersonNotFoundException {
        Optional<Person> personToBeDeleted=repo
                .findById(id);
        if (personToBeDeleted.isPresent()){
            repo.deleteById(id);
            return "Person with id: "+id+" was deleted";
        }
        else throw new PersonNotFoundException("Person with id: "+id+" doesnt exist");
    }
}
