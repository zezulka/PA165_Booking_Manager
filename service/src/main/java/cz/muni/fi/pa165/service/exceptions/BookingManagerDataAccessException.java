package cz.muni.fi.pa165.service.exceptions;

import org.springframework.dao.DataAccessException;

/**
 *
 * Roofing exception to be thrown when any exception is thrown at the DAO layer.
 * 
 * @author Miloslav Zezulka
 */
public class BookingManagerDataAccessException extends DataAccessException {

    public BookingManagerDataAccessException(String msg) {
        super(msg);
    }

    public BookingManagerDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
