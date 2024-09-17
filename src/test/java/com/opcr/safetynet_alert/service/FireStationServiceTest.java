package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.exceptions.FireStationAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.FireStationNotFoundException;
import com.opcr.safetynet_alert.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    private JsonDataUtils jsonDataUtils;

    @InjectMocks
    private FireStationService fireStationService;

    private FireStation fireStationTest;

    @BeforeEach
    public void init(){
        this.fireStationTest = null;
        ArrayList<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("TWO Street", 1));
        fireStations.add(new FireStation("THREE Road", 3));
        when(jsonDataUtils.getFireStations()).thenReturn(fireStations);
    }

    @Test
    public void addFireStationTest() {
        fireStationTest = new FireStation("NINE Street", 1);
        fireStationService.addFireStation(fireStationTest);
        verify(jsonDataUtils, Mockito.times(1)).updateFireStations(any(ArrayList.class));
    }

    @Test
    public void addFireStationAlreadyExistingTest() {
        fireStationTest = new FireStation("THREE Road", 3);
        assertThrows(FireStationAlreadyExistException.class, () -> fireStationService.addFireStation(fireStationTest));
    }

    @Test
    public void addFireStationNullTest(){
        assertThrows(NullPointerException.class, () -> fireStationService.addFireStation(null));
    }

    @Test
    public void deleteFireStationTest() {
        fireStationTest = new FireStation("THREE Road", 3);
        fireStationService.deleteFireStation(fireStationTest);
        verify(jsonDataUtils, Mockito.times(1)).updateFireStations(any(ArrayList.class));
    }

    @Test
    public void deleteFireStationNotExistingTest() {
        fireStationTest = new FireStation("NINE Street", 1);
        assertThrows(FireStationNotFoundException.class, () -> fireStationService.deleteFireStation(fireStationTest));
    }

    @Test
    public void deleteFireStationNullTest(){
        assertThrows(NullPointerException.class, () -> fireStationService.deleteFireStation(null));
    }

    @Test
    public void updateFireStationTest() {
        fireStationTest = new FireStation("THREE Road", 10);
        fireStationService.updateFireStation(fireStationTest);
        verify(jsonDataUtils, Mockito.times(1)).updateFireStations(any(ArrayList.class));
    }

    @Test
    public void updateFireStationNotExistingTest() {
        fireStationTest = new FireStation("NINE Street", 1);
        assertThrows(FireStationNotFoundException.class, () -> fireStationService.updateFireStation(fireStationTest));
    }

    @Test
    public void updateFireStationNullTest() {
        assertThrows(NullPointerException.class, () -> fireStationService.updateFireStation(null));
    }
}
