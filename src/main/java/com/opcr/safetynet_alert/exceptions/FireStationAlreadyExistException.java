package com.opcr.safetynet_alert.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FireStationAlreadyExistException extends RuntimeException{

    private static final Logger logger = LogManager.getLogger(FireStationAlreadyExistException.class);

    public FireStationAlreadyExistException(String exceptionMessage){
        logger.error("ERROR FireStation already exist : %s".formatted(exceptionMessage));
    }
}
