package cz.muni.fi.pa165.web.restapi.exception;

public class JsonErrorMessage {

    private String code;
    private String message;

    public JsonErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorResource{"
                + "code='" + code + '\''
                + ", message='" + message + '\''
                + '}';
    }
}
