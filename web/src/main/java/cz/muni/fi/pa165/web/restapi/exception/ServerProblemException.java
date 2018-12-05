package cz.muni.fi.pa165.web.restapi.exception;

public class ServerProblemException extends RestApiException {

    public ServerProblemException(String message) {
        super(message);
    }
}
