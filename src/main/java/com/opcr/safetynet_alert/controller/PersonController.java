package com.opcr.safetynet_alert.controller;

import com.opcr.safetynet_alert.model.Person;
import com.opcr.safetynet_alert.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @PutMapping
    public ResponseEntity<String> addPerson(@RequestBody Person person){
        logger.info("PUT Request /person {}",person.toString());
        if (personService.addPerson(person)) {
            logger.info("PUT Request completed successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("Person Added.");
        }else{
            logger.error("PUT Request failed.");
            return ResponseEntity.badRequest().body("Person already existing.");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestBody Person person){
        logger.info("DELETE Request /person {}",person.toString());
        if(personService.deletePerson(person)){
            logger.info("DELETE Request completed successfully.");
            return ResponseEntity.noContent().build();
        }else{
            logger.error("DELETE Request failed.");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> updatePerson(@RequestBody Person person){
        logger.info("POST Request /person {}",person);
        if(personService.updatePerson(person)){
            logger.info("POST Request completed successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("Person updated.");
        }else{
            logger.error("POST Request failed.");
            return ResponseEntity.notFound().build();
        }
    }
}
