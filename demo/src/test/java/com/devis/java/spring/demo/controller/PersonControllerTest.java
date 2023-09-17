package com.devis.java.spring.demo.controller;

import com.devis.java.spring.demo.errors.PersonNotFoundException;
import com.devis.java.spring.demo.models.Person;
import com.devis.java.spring.demo.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class PersonControllerTest {

    @MockBean
    PersonService service;
    private Person person;
    @Autowired
    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        person= Person.builder().name("devis").age(18).id(1l).build();
    }

    @Test
    @DisplayName("When save person endpoint is hit status is ok")
    void savePerson() throws Exception {
        Person inputperson= Person.builder().name("devis").age(18).build();
        Mockito.when(service.savePerson(inputperson)).thenReturn(person);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/person").contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                " \"name\":\"Devis\",\n" +
                " \"age\":18,\n" +
                " \"address\":\"Tirana\"\n" +
                " }")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("When get person by id endpoint is hit right data is returned")
    void getPersonById() throws Exception {
        Mockito.when(service.getPerson(1l)).thenReturn(person);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/person/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                .value(person.getName()));
    }
}