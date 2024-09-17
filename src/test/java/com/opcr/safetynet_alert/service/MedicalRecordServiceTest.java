package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.exceptions.MedicalRecordAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.MedicalRecordNotFoundException;
import com.opcr.safetynet_alert.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @Mock
    private JsonDataUtils jsonDataUtils;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private MedicalRecord medicalRecordTest;

    @BeforeEach
    public void init(){
        this.medicalRecordTest = null;
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();

        medicalRecords.add(new MedicalRecord("Esteban","Test1","03/06/1989",new ArrayList<>(Arrays.asList("Fish","Meat")),new ArrayList<>()));
        medicalRecords.add(new MedicalRecord("Fran","Test2","20/05/1991",new ArrayList<>(Arrays.asList("Fish","Nut")),new ArrayList<>(Arrays.asList("terazine:10mg", "noznazol:250mg"))));
        medicalRecords.add(new MedicalRecord("Michel","Test3","03/11/1970",new ArrayList<>(),new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"))));
        medicalRecords.add(new MedicalRecord("Blanca","Test3","09/05/2001",new ArrayList<>(),new ArrayList<>()));
        when(jsonDataUtils.getMedicalRecords()).thenReturn(medicalRecords);
    }

    @Test
    public void addMedicalRecordTest() {
        medicalRecordTest = new MedicalRecord("New","Test4","19/05/2004",new ArrayList<>(),new ArrayList<>());
        medicalRecordService.addMedicalRecord(medicalRecordTest);
        verify(jsonDataUtils, Mockito.times(1)).updateMedicalRecords(any(ArrayList.class));
    }

    @Test
    public void addMedicalRecordAlreadyExistingTest() {
        medicalRecordTest = new MedicalRecord("Esteban","Test1","03/06/1989",new ArrayList<>(),new ArrayList<>());
        assertThrows(MedicalRecordAlreadyExistException.class, () -> medicalRecordService.addMedicalRecord(medicalRecordTest));
    }

    @Test
    public void addMedicalRecordNullTest(){
        assertThrows(NullPointerException.class, () -> medicalRecordService.addMedicalRecord(null));
    }

    @Test
    public void deleteMedicalRecordTest() {
        medicalRecordTest = new MedicalRecord("Esteban","Test1","03/06/1989",new ArrayList<>(),new ArrayList<>());
        medicalRecordService.deleteMedicalRecord(medicalRecordTest);
        verify(jsonDataUtils, Mockito.times(1)).updateMedicalRecords(any(ArrayList.class));
    }

    @Test
    public void deleteMedicalRecordNotExistingTest() {
        medicalRecordTest = new MedicalRecord("New","Test4","19/05/2004",new ArrayList<>(),new ArrayList<>());
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.deleteMedicalRecord(medicalRecordTest));
    }

    @Test
    public void deleteMedicalRecordNullTest(){
        assertThrows(NullPointerException.class, () -> medicalRecordService.deleteMedicalRecord(null));
    }

    @Test
    public void updateMedicalRecordTest() {
        medicalRecordTest = new MedicalRecord("Michel","Test3","03/11/1970",new ArrayList<>(Arrays.asList("Allergies1","Allergies2")),new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")));
        medicalRecordService.updateMedicalRecord(medicalRecordTest);
        verify(jsonDataUtils, Mockito.times(1)).updateMedicalRecords(any(ArrayList.class));
    }

    @Test
    public void updateMedicalRecordNotExistingTest() {
        medicalRecordTest = new MedicalRecord("New","Test4","19/05/2004",new ArrayList<>(),new ArrayList<>());
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.updateMedicalRecord(medicalRecordTest));
    }

    @Test
    public void updateMedicalRecordNullTest() {
        assertThrows(NullPointerException.class, () -> medicalRecordService.updateMedicalRecord(null));
    }
}
