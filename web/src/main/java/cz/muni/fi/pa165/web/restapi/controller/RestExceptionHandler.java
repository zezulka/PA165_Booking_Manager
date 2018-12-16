package cz.muni.fi.pa165.web.restapi.controller;

import cz.muni.fi.pa165.web.restapi.exception.JsonErrorMessage;
import cz.muni.fi.pa165.web.restapi.exception.RestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    @ResponseBody
    protected ResponseEntity<JsonErrorMessage> handleProblem(Exception e) {

        JsonErrorMessage error = new JsonErrorMessage(e.getClass().getSimpleName(), e.getMessage());
        if (e instanceof RestApiException) {
            RestApiException ex = (RestApiException) e;
            LOGGER.debug("handleProblem({}(\"{}\")) httpStatus={}", e.getClass().getName(), e.getMessage(), ex.getHttpStatus());
            return new ResponseEntity<>(error, ex.getHttpStatus());
        }
        /**
         * If any other exception is thrown then it most probably means bug in the code.
         */
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
