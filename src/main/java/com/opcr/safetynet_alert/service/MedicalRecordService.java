package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.exceptions.FireStationAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.FireStationNotFoundException;
import com.opcr.safetynet_alert.exceptions.MedicalRecordAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.MedicalRecordNotFoundException;
import com.opcr.safetynet_alert.model.FireStation;
import com.opcr.safetynet_alert.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MedicalRecordService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    public void addMedicalRecord(MedicalRecord medicalRecordToAdd) throws MedicalRecordAlreadyExistException {
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();

        if (!medicalRecords.contains(medicalRecordToAdd)) {
            medicalRecords.add(medicalRecordToAdd);
            jsonDataUtils.updateMedicalRecords(medicalRecords);
        }else {
            throw new MedicalRecordAlreadyExistException(medicalRecordToAdd.toString());
        }
    }

    public void deleteMedicalRecord(MedicalRecord medicalRecordToDelete) throws MedicalRecordNotFoundException {
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();

        if (medicalRecords.contains(medicalRecordToDelete)) {
            medicalRecords.remove(medicalRecordToDelete);
            jsonDataUtils.updateMedicalRecords(medicalRecords);
        }else {
            throw new MedicalRecordNotFoundException(medicalRecordToDelete.toString());
        }
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordNotFoundException {
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();

        if (medicalRecords.contains(medicalRecord)) {
            for (MedicalRecord mr : medicalRecords.stream().filter(mr -> mr.getFirstName().equals(medicalRecord.getFirstName())
                    && mr.getLastName().equals(medicalRecord.getLastName())).toList()) {
                    mr.setBirthdate(medicalRecord.getBirthdate());
                    mr.setMedications(medicalRecord.getMedications());
                    mr.setAllergies(medicalRecord.getAllergies());
                    jsonDataUtils.updateMedicalRecords(medicalRecords);
                    break;
            }
        }else {
            throw new MedicalRecordNotFoundException(medicalRecord.toString());
        }
    }
}
