package com.opcr.safetynet_alert.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FireStationNotFoundException extends RuntimeException{

    private static final Logger logger = LogManager.getLogger(FireStationNotFoundException.class);

    public FireStationNotFoundException(String exceptionMessage){
        logger.error("ERROR FireStation not found : %s".formatted(exceptionMessage));
    }
}
