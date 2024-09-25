package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.exceptions.FireStationAlreadyExistException;
import com.opcr.safetynet_alert.exceptions.FireStationNotFoundException;
import com.opcr.safetynet_alert.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FireStationService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    /**
     * Add a FireStation if fireStationToAdd does not exist in the list of FireStation.
     *
     * @param fireStationToAdd FireStation to add.
     * @throws FireStationAlreadyExistException if fireStationToAdd already exist.
     **/
    public void addFireStation(FireStation fireStationToAdd) throws FireStationAlreadyExistException {
        ArrayList<FireStation> fireStations = jsonDataUtils.getFireStations();
        if (!fireStations.contains(fireStationToAdd)) {
            fireStations.add(fireStationToAdd);
            jsonDataUtils.updateFireStations(fireStations);
        } else {
            throw new FireStationAlreadyExistException(fireStationToAdd.toString());
        }
    }

    /**
     * Delete a FireStation if fireStationToDelete exist in the list of FireStation.
     *
     * @param fireStationToDelete FireStation to delete.
     * @throws FireStationNotFoundException if fireStationToDelete is not found.
     **/
    public void deleteFireStation(FireStation fireStationToDelete) throws FireStationNotFoundException {
        ArrayList<FireStation> fireStations = jsonDataUtils.getFireStations();
        if (fireStations.contains(fireStationToDelete)) {
            fireStations.remove(fireStationToDelete);
            jsonDataUtils.updateFireStations(fireStations);
        } else {
            throw new FireStationNotFoundException(fireStationToDelete.toString());
        }
    }

    /**
     * Update a FireStation if one with the same address exist in the list of FireStation.
     *
     * @param fireStationToUpdate FireStation to update.
     * @throws FireStationNotFoundException if fireStationToUpdate is not found.
     **/
    public void updateFireStation(FireStation fireStationToUpdate) throws FireStationNotFoundException {
        ArrayList<FireStation> fireStations = jsonDataUtils.getFireStations();
        if (fireStations.stream().anyMatch(f -> f.getAddress().equals(fireStationToUpdate.getAddress()))) {
            for (FireStation f : fireStations.stream().filter(f -> f.getAddress().equals(fireStationToUpdate.getAddress())).toList()) {
                f.setStation(fireStationToUpdate.getStation());
                jsonDataUtils.updateFireStations(fireStations);
                break;
            }
        } else {
            throw new FireStationNotFoundException(fireStationToUpdate.toString());
        }
    }
}
