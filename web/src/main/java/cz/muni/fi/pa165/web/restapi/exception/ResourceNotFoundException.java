package cz.muni.fi.pa165.web.restapi.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RestApiException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
