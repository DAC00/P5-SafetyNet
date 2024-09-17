package com.opcr.safetynet_alert.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonAlreadyExistException extends RuntimeException{
    private static final Logger logger = LogManager.getLogger(PersonAlreadyExistException.class);

    public PersonAlreadyExistException(String exceptionMessage){
        logger.error("ERROR Person already exist : %s".formatted(exceptionMessage));
    }
}
