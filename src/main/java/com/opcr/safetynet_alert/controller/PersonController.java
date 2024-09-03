package com.opcr.safetynet_alert.controller;

import com.opcr.safetynet_alert.model.Person;
import com.opcr.safetynet_alert.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PutMapping
    public void addPerson(@RequestBody Person person){
        personService.addPerson(person);
    }

    @DeleteMapping
    public void deletePerson(@RequestBody Person person){
        personService.deletePerson(person);
    }

    @PostMapping
    public void updatePerson(@RequestBody Person person){
        personService.updatePerson(person);
    }

//    @GetMapping
//    public ArrayList<Person> getPersons(){
//        return personService.getPersons();
//    }
}
