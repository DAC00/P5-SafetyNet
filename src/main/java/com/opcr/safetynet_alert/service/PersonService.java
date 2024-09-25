package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.exceptions.PersonAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.PersonNotFoundException;
import com.opcr.safetynet_alert.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PersonService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    /**
     * Add a Person if personToAdd does not exist in the list of Person.
     *
     * @param personToAdd Person to add.
     * @throws PersonAlreadyExistException if personToAdd already exist.
     **/
    public void addPerson(Person personToAdd) throws PersonAlreadyExistException {
        ArrayList<Person> persons = jsonDataUtils.getPersons();
        if (!persons.contains(personToAdd)) {
            persons.add(personToAdd);
            jsonDataUtils.updatePersons(persons);
        } else {
            throw new PersonAlreadyExistException(personToAdd.toString());
        }
    }

    /**
     * Delete a Person if personToDelete exist in the list of Person.
     *
     * @param personToDelete Person to delete.
     * @throws PersonNotFoundException if personToDelete is not found.
     **/
    public void deletePerson(Person personToDelete) throws PersonNotFoundException {
        ArrayList<Person> persons = jsonDataUtils.getPersons();
        if (persons.contains(personToDelete)) {
            persons.remove(personToDelete);
            jsonDataUtils.updatePersons(persons);
        } else {
            throw new PersonNotFoundException(personToDelete.toString());
        }
    }

    /**
     * Update information of a Person if someone with the same last name and first name exist in the list of Person.
     *
     * @param personToUpdate Person to update.
     * @throws PersonNotFoundException if personToUpdate is not found.
     **/
    public void updatePerson(Person personToUpdate) throws PersonNotFoundException {
        ArrayList<Person> persons = jsonDataUtils.getPersons();
        if (persons.contains(personToUpdate)) {
            for (Person p : persons.stream().filter(p -> p.getFirstName().equals(personToUpdate.getFirstName())
                    && p.getLastName().equals(personToUpdate.getLastName())).toList()) {
                p.setAddress(personToUpdate.getAddress());
                p.setCity(personToUpdate.getCity());
                p.setEmail(personToUpdate.getEmail());
                p.setPhone(personToUpdate.getPhone());
                p.setZip(personToUpdate.getZip());
                jsonDataUtils.updatePersons(persons);
                break;
            }
        } else {
            throw new PersonNotFoundException(personToUpdate.toString());
        }
    }
}