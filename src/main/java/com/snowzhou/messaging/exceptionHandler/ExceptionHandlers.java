package com.snowzhou.messaging.exceptionHandler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class ExceptionHandlers {

    @ExceptionHandler(IllegalArgumentException.class)
    // handle illegalArgumentException
    public ResponseEntity<String> handleException(IllegalArgumentException illegalArgumentException){
        log.warn("Encountered an illegal argument exception: {}",
                illegalArgumentException.getMessage(),
                illegalArgumentException);
        return ResponseEntity
                .badRequest()
                .body(illegalArgumentException.getMessage());

    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception exception){
        log.warn("Encountered an illegal argument exception: {}",
                exception.getMessage(),
                exception);
        return ResponseEntity
                .internalServerError()
                .body(exception.getMessage());
    }

}
