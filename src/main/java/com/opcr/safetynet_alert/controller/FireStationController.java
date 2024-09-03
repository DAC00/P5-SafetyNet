package com.opcr.safetynet_alert.controller;

import com.opcr.safetynet_alert.model.FireStation;
import com.opcr.safetynet_alert.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private final FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PutMapping
    public void addFireStation(@RequestBody FireStation firestation){
        fireStationService.addFireStation(firestation);
    }

    @DeleteMapping
    public void deleteFireStation(@RequestBody FireStation firestation){
        fireStationService.deleteFireStation(firestation);
    }

    @PostMapping
    public void updateFireStation(@RequestBody FireStation firestation){
        fireStationService.updateFireStation(firestation);
    }
}
