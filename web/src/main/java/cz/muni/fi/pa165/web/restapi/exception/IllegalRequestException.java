package cz.muni.fi.pa165.web.restapi.exception;

public class IllegalRequestException extends RuntimeException {

    public IllegalRequestException(String message) {
        super(message);
    }
}
