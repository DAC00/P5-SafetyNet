package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PersonService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    public void addPerson(Person personToAdd){
        ArrayList<Person> persons = jsonDataUtils.getPersons();
        if(!persons.contains(personToAdd)){
            persons.add(personToAdd);
            jsonDataUtils.updatePersons(persons);
            System.out.println(personToAdd.toString() + " : Added");
        }else {
            System.out.println(personToAdd.toString() + " : Already existing");
        }
    }

    public void deletePerson(Person personToDelete){
        ArrayList<Person> persons = jsonDataUtils.getPersons();
        if(persons.contains(personToDelete)){
            persons.remove(personToDelete);
            jsonDataUtils.updatePersons(persons);
            System.out.println(personToDelete.toString() + " : Deleted");
        }else {
            System.out.println(personToDelete.toString() + " : Not existing");
        }
    }

    public void updatePerson(Person personToUpdate){
        ArrayList<Person> persons = jsonDataUtils.getPersons();
        for(Person p : persons){
            if(p.getFirstName().equals(personToUpdate.getFirstName()) &&
                    p.getLastName().equals(personToUpdate.getLastName())){
                p.setAddress(personToUpdate.getAddress());
                p.setCity(personToUpdate.getCity());
                p.setEmail(personToUpdate.getEmail());
                p.setPhone(personToUpdate.getPhone());
                p.setZip(personToUpdate.getZip());
                jsonDataUtils.updatePersons(persons);
                System.out.println(personToUpdate.toString() + " : Updated");
                break;
            }
        }
    }
}
