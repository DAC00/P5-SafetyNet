package com.opcr.safetynet_alert.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MedicalRecordAlreadyExistException extends RuntimeException{
    private static final Logger logger = LogManager.getLogger(MedicalRecordAlreadyExistException.class);

    public MedicalRecordAlreadyExistException(String exceptionMessage){
        logger.error("ERROR MedicalRecord already exist : %s".formatted(exceptionMessage));
    }
}
