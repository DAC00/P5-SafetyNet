package com.opcr.safetynet_alert.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonNotFoundException extends RuntimeException{
    private static final Logger logger = LogManager.getLogger(PersonNotFoundException.class);

    public PersonNotFoundException(String exceptionMessage){
        logger.error("ERROR Person not found : %s".formatted(exceptionMessage));
    }
}
