
// @author Martin Palenik

package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
     * Calculate the accommodation price.
     *
     * @param booking booking of given user in given time range
     * @throws IllegalArgumentException any argument is null
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return total accomodation price, zero otherwise
     */
    BigDecimal getTotalPrice(Booking booking);
    
    /**
     *  Finds bookings by room.
     * 
     * @param room must not be null
     * @throws IllegalArgumentException {@code room} is null
     * @throw BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return bookings for the given room, empty List if there are none
     */
    List<Booking> findByRoom(Room room);
}