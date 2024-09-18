package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.exceptions.MedicalRecordAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.MedicalRecordNotFoundException;
import com.opcr.safetynet_alert.exceptions.PersonAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.PersonNotFoundException;
import com.opcr.safetynet_alert.model.MedicalRecord;
import com.opcr.safetynet_alert.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PersonService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    public void addPerson(Person personToAdd) throws PersonAlreadyExistException {
        ArrayList<Person> persons = jsonDataUtils.getPersons();

        if (!persons.contains(personToAdd)) {
            persons.add(personToAdd);
            jsonDataUtils.updatePersons(persons);
        } else {
            throw new PersonAlreadyExistException(personToAdd.toString());
        }
    }

    public void deletePerson(Person personToDelete) throws PersonNotFoundException {
        ArrayList<Person> persons = jsonDataUtils.getPersons();

        if (persons.contains(personToDelete)) {
            persons.remove(personToDelete);
            jsonDataUtils.updatePersons(persons);
        }else {
            throw new PersonNotFoundException(personToDelete.toString());
        }
    }

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
        }else {
            throw new PersonNotFoundException(personToUpdate.toString());
        }
    }
}