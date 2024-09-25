package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.exceptions.MedicalRecordAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.MedicalRecordNotFoundException;
import com.opcr.safetynet_alert.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MedicalRecordService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    /**
     * Add a MedicalRecord if medicalRecordToAdd does not exist in the list of MedicalRecord.
     *
     * @param medicalRecordToAdd MedicalRecord to add.
     * @throws MedicalRecordAlreadyExistException if medicalRecordToAdd already exist.
     **/
    public void addMedicalRecord(MedicalRecord medicalRecordToAdd) throws MedicalRecordAlreadyExistException {
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();
        if (!medicalRecords.contains(medicalRecordToAdd)) {
            medicalRecords.add(medicalRecordToAdd);
            jsonDataUtils.updateMedicalRecords(medicalRecords);
        } else {
            throw new MedicalRecordAlreadyExistException(medicalRecordToAdd.toString());
        }
    }

    /**
     * Delete a MedicalRecord if medicalRecordToDelete exist in the list of MedicalRecord.
     *
     * @param medicalRecordToDelete MedicalRecord to delete.
     * @throws MedicalRecordNotFoundException if medicalRecordToDelete is not found.
     **/
    public void deleteMedicalRecord(MedicalRecord medicalRecordToDelete) throws MedicalRecordNotFoundException {
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();
        if (medicalRecords.contains(medicalRecordToDelete)) {
            medicalRecords.remove(medicalRecordToDelete);
            jsonDataUtils.updateMedicalRecords(medicalRecords);
        } else {
            throw new MedicalRecordNotFoundException(medicalRecordToDelete.toString());
        }
    }

    /**
     * Update a MedicalRecord if one with the same address exist in the list of MedicalRecord.
     *
     * @param medicalRecordToUpdate MedicalRecord to update.
     * @throws MedicalRecordNotFoundException if medicalRecordToUpdate is not found.
     **/
    public void updateMedicalRecord(MedicalRecord medicalRecordToUpdate) throws MedicalRecordNotFoundException {
        ArrayList<MedicalRecord> medicalRecords = jsonDataUtils.getMedicalRecords();
        if (medicalRecords.contains(medicalRecordToUpdate)) {
            for (MedicalRecord mr : medicalRecords.stream().filter(mr -> mr.getFirstName().equals(medicalRecordToUpdate.getFirstName())
                    && mr.getLastName().equals(medicalRecordToUpdate.getLastName())).toList()) {
                mr.setBirthdate(medicalRecordToUpdate.getBirthdate());
                mr.setMedications(medicalRecordToUpdate.getMedications());
                mr.setAllergies(medicalRecordToUpdate.getAllergies());
                jsonDataUtils.updateMedicalRecords(medicalRecords);
                break;
            }
        } else {
            throw new MedicalRecordNotFoundException(medicalRecordToUpdate.toString());
        }
    }
}
