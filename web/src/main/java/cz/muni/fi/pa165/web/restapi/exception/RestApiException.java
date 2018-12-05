package cz.muni.fi.pa165.web.restapi.exception;

import org.springframework.http.HttpStatus;

public class RestApiException extends Exception {

    public RestApiException(String message) {
        super(message);
    }

    /**
     * Tells which HttpStatus should be returned for the given exception.
     */
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
