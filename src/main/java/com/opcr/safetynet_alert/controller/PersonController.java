package com.opcr.safetynet_alert.controller;

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

    @Autowired
    private PersonService personService;

    @PutMapping
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        logger.info("PUT Request /person : %s".formatted(person.toString()));
        try {
            personService.addPerson(person);
            logger.info("PUT Request completed successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("Person added.");
        } catch (PersonAlreadyExistException e) {
            String errorMessage = "Person already exist : %s".formatted(e.getMessage());
            logger.error(errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestBody Person person) {
        logger.info("DELETE Request /person : %s".formatted(person.toString()));
        try {
            personService.deletePerson(person);
            logger.info("DELETE Request completed successfully.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Person deleted.");
        } catch (PersonNotFoundException e) {
            String errorMessage = "Person not found : %s".formatted(e.getMessage());
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PostMapping
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {
        logger.info("POST Request /person : %s".formatted(person));
        try {
            personService.updatePerson(person);
            logger.info("POST Request completed successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("Person updated.");
        } catch (PersonNotFoundException e) {
            String errorMessage = "Person not found : %s".formatted(e.getMessage());
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
