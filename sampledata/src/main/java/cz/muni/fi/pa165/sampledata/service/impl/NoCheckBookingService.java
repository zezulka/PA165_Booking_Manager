package cz.muni.fi.pa165.sampledata.service.impl;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.service.BookingServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Miloslav Zezulka
 */
@Service(value = "noCheck")
public class NoCheckBookingService extends BookingServiceImpl {
    
    /**
     * Disabling this check in the parent implementation enables us
     * to insert bookings into database which were done in the past.
     * 
     * @return always true
     */
    @Override
    protected boolean bookingInFuture(Booking _ignored) {
        return true;
    }
}
