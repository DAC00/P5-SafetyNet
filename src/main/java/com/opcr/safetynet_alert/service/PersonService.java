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

    public void addPerson(Person person){
    }

    public void deletePerson(Person person){
    }

    public void updatePerson(Person person){
    }

//    public ArrayList<Person> getPersons(){
//        return jsonDataUtils.getPersons();
//    }
}
