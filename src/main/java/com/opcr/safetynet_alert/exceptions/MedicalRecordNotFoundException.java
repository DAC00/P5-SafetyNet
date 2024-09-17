package com.opcr.safetynet_alert.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MedicalRecordNotFoundException extends RuntimeException{
    private static final Logger logger = LogManager.getLogger(MedicalRecordNotFoundException.class);

    public MedicalRecordNotFoundException(String exceptionMessage){
        logger.error("ERROR MedicalRecord not found : %s".formatted(exceptionMessage));
    }
}
