package com.opcr.safetynet_alert.service;

import com.opcr.safetynet_alert.JsonDataUtils;
import com.opcr.safetynet_alert.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FireStationService {

    @Autowired
    private JsonDataUtils jsonDataUtils;

    public boolean addFireStation(FireStation fireStation){
        ArrayList<FireStation> fireStations = jsonDataUtils.getFireStations();
        if(!fireStations.contains(fireStation)){
            fireStations.add(fireStation);
            jsonDataUtils.updateFireStations(fireStations);
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteFireStation(FireStation fireStation){
        ArrayList<FireStation> fireStations = jsonDataUtils.getFireStations();
        if(fireStations.contains(fireStation)){
            fireStations.remove(fireStation);
            jsonDataUtils.updateFireStations(fireStations);
            return true;
        }else {
            return false;
        }
    }

    public boolean updateFireStation(FireStation fireStation){
        ArrayList<FireStation> fireStations = jsonDataUtils.getFireStations();
        for(FireStation f : fireStations){
            if (f.getAddress().equals(fireStation.getAddress())){
                f.setStation(fireStation.getStation());
                jsonDataUtils.updateFireStations(fireStations);
                return true;
            }
        }
        return false;
    }
}
