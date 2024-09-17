package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.exceptions.PersonAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.PersonNotFoundException;
import com.opcr.safetynet_alert.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private JsonDataUtils jsonDataUtils;

    @InjectMocks
    private PersonService personService;

    private Person personTest;

    @BeforeEach
    public void init() {
        this.personTest = null;
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"));
        persons.add(new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"));
        persons.add(new Person("Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        when(jsonDataUtils.getPersons()).thenReturn(persons);
    }

    @Test
    public void addPersonTest() {
        personTest = new Person("Test1", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6543", "test1@email.com");
        personService.addPerson(personTest);
        verify(jsonDataUtils, Mockito.times(1)).updatePersons(any(ArrayList.class));
    }

    @Test
    public void addPersonAlreadyExistingTest() {
        personTest = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com");
        assertThrows(PersonAlreadyExistException.class, () -> personService.addPerson(personTest));
    }

    @Test
    public void addPersonNullTest() {
        assertThrows(NullPointerException.class, () -> personService.addPerson(null));
    }

    @Test
    public void deletePersonTest() {
        personTest = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com");
        personService.deletePerson(personTest);
        verify(jsonDataUtils, Mockito.times(1)).updatePersons(any(ArrayList.class));
    }

    @Test
    public void deletePersonNotExistingTest() {
        personTest = new Person("Test1", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6543", "test1@email.com");
        assertThrows(PersonNotFoundException.class, () -> personService.deletePerson(personTest));
    }

    @Test
    public void deletePersonNullTest() {
        assertThrows(NullPointerException.class, () -> personService.deletePerson(null));
    }

    @Test
    public void updatePersonTest() {
        personTest = new Person("Jacob", "Boyd", "1 test", "Culver", "97451", "841-874-6513", "newtest@email.com");
        personService.updatePerson(personTest);
        verify(jsonDataUtils, Mockito.times(1)).updatePersons(any(ArrayList.class));
    }

    @Test
    public void updatePersonNotExistingTest() {
        personTest = new Person("Test1", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6543", "test1@email.com");
        assertThrows(PersonNotFoundException.class, () -> personService.updatePerson(personTest));
    }

    @Test
    public void updatePersonNullTest() {
        assertThrows(NullPointerException.class, () -> personService.updatePerson(null));
    }
}
