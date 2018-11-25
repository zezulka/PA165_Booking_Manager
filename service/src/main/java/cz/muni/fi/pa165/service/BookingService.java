package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author Martin Palenik
 */
@Service
public interface BookingService {

    /**
     * Create a booking.
     *
     * @param booking
     * @throws IllegalArgumentException any argument is null
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     */
    void book(Booking booking);

    /**
     * Cancel a booking.
     *
     * @param booking
     * @throws IllegalArgumentException any argument is null
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     */
    void cancel(Booking booking);

    /**
     * Return all bookings currently in the system.
     *
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return {@link List} of bookings. Empty list if there are none.
     */
    List<Booking> getAll();

    /**
     * Calculate the accommodation price after discount. If the discount
     * exceeds the base price, BigDecimal.ZERO is returned instead.
     *
     * @param booking booking of given user in given time range
     * @throws IllegalArgumentException {@code booking} is null
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return total accommodation price after discount
     */
    BigDecimal getTotalPrice(Booking booking);
}