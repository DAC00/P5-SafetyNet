package com.opcr.safetynet_alert.controller;

import com.opcr.safetynet_alert.exceptions.MedicalRecordAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.MedicalRecordNotFoundException;
import com.opcr.safetynet_alert.exceptions.PersonAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.PersonNotFoundException;
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
        try {
            personService.addPerson(person);
            logger.info("PUT Request completed successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("Person Added.");
        }catch (PersonAlreadyExistException e){
            return ResponseEntity.badRequest().body("Person already exist.");
        }catch (NullPointerException e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("Person is null.");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestBody Person person){
        logger.info("DELETE Request /person {}",person.toString());
        try {
            personService.deletePerson(person);
            logger.info("DELETE Request completed successfully.");
            return ResponseEntity.noContent().build();
        }catch (PersonNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }catch (NullPointerException e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("Person is null.");
        }
    }

    @PostMapping
    public ResponseEntity<String> updatePerson(@RequestBody Person person){
        logger.info("POST Request /person {}",person);
        try {
            personService.updatePerson(person);
            logger.info("POST Request completed successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("Person updated.");
        }catch (PersonNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }catch (NullPointerException e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("Person is null.");
        }
    }
}
