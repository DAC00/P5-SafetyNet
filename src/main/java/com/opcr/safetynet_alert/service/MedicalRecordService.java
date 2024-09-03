package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.model.FireStation;
import com.opcr.safetynet_alert.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MedicalRecordService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    public void addMedicalRecord(MedicalRecord medicalRecordToAdd){
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();
        if(!medicalRecords.contains(medicalRecordToAdd)){
            medicalRecords.add(medicalRecordToAdd);
            jsonDataUtils.updateMedicalRecords(medicalRecords);
            System.out.println(medicalRecordToAdd.toString() + " : Added");
        }else {
            System.out.println(medicalRecordToAdd.toString() + " : Already existing");
        }
    }

    public void deleteMedicalRecord(MedicalRecord medicalRecordToDelete){
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();
        if(medicalRecords.contains(medicalRecordToDelete)){
            medicalRecords.remove(medicalRecordToDelete);
            jsonDataUtils.updateMedicalRecords(medicalRecords);
            System.out.println(medicalRecordToDelete.toString() + " : Deleted");
        }else {
            System.out.println(medicalRecordToDelete.toString() + " : Not existing");
        }
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord){
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();
        for(MedicalRecord mr : medicalRecords){
            if(mr.getFirstName().equals(medicalRecord.getFirstName()) &&
                    mr.getLastName().equals(medicalRecord.getLastName())){
                mr.setBirthdate(medicalRecord.getBirthdate());
                mr.setMedications(medicalRecord.getMedications());
                mr.setAllergies(medicalRecord.getAllergies());
                jsonDataUtils.updateMedicalRecords(medicalRecords);
                System.out.println(medicalRecord.toString() + " : Updated");
                break;
            }
        }
    }
}
