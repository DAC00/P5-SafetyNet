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

    public void addFireStation(FireStation fireStation) {
        ArrayList<FireStation> fireStations = jsonDataUtils.getFireStations();

        if (!fireStations.contains(fireStation) && fireStation != null) {
            fireStations.add(fireStation);
            jsonDataUtils.updateFireStations(fireStations);
        } else if (fireStation == null) {
            throw new NullPointerException();
        } else {
            throw new FireStationAlreadyExistException(fireStation.toString());
        }
    }

    public void deleteFireStation(FireStation fireStation) {
        ArrayList<FireStation> fireStations = jsonDataUtils.getFireStations();

        if (fireStations.contains(fireStation) && fireStation != null) {
            fireStations.remove(fireStation);
            jsonDataUtils.updateFireStations(fireStations);
        } else if (fireStation == null) {
            throw new NullPointerException();
        } else {
            throw new FireStationNotFoundException(fireStation.toString());
        }
    }

    public void updateFireStation(FireStation fireStation) {
        ArrayList<FireStation> fireStations = jsonDataUtils.getFireStations();

        if(fireStation != null){
            if(fireStations.stream().anyMatch(f -> f.getAddress().equals(fireStation.getAddress()))){
                for (FireStation f : fireStations.stream().filter(f -> f.getAddress().equals(fireStation.getAddress())).toList()) {
                    f.setStation(fireStation.getStation());
                    jsonDataUtils.updateFireStations(fireStations);
                    break;
                }
            }else {
                throw new FireStationNotFoundException(fireStation.toString());
            }
        }else {
            throw new NullPointerException();
        }
    }
}
