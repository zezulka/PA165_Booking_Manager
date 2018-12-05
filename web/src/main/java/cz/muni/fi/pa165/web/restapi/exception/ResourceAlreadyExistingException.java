package cz.muni.fi.pa165.web.restapi.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistingException extends RestApiException {

    public ResourceAlreadyExistingException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
