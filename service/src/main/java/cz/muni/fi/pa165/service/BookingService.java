package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.User;
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
     * Create a valid booking. Booking is valid only if it is created with the
     * starting date of today and no other existing booking overlaps with it.
     *
     * @param booking
     * @throws IllegalArgumentException any argument is null or booking is not valid
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
     * Update a booking.
     * @throws IllegalArgumentException any argument is null
     * @param booking
     */
    void update(Booking booking);

    /**
     * Return all bookings currently in the system.
     *
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return {@link List} of bookings. Empty list if there are none.
     */
    List<Booking> getAll();
    
    /**
     * Finds the booking by its id.
     * @param id database identifier, must not be null
     * @return Booking with the given database identifier, null if there is no such booking
     */
    Booking findById(Long id);

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
    
    /**
     *  Finds bookings by room.
     * 
     * @param room must not be null
     * @throws IllegalArgumentException {@code room} is null
     * @throw BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return bookings for the given room, empty List if there are none
     */
    List<Booking> findByRoom(Room room);
    
    /**
     *  Finds bookings by user.
     * 
     * @param user must not be null
     * @throws IllegalArgumentException {@code user} is null
     * @throw BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return bookings for the given user, empty List if there are none
     */
    List<Booking> findByUser(User user);
}