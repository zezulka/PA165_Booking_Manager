package cz.muni.fi.pa165.service;

import java.util.List;

import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;

/**
 * Service used when the administrator wishes to search for a certain
 * hotel room availability at a certain time range.
 *
 * The administrator should also be able to see the list of bookings made for
 * a given time period.
 * 
 * @author Miloslav Zezulka
 */
public interface RoomAvailabilityService {
    /**
     * @param range range to search for bookings, must not be null
     * @param room must not be null
     * @throws IllegalArgumentException {@code range} or {@code room} is null
     * @return List of {@link Booking}s for the given {@code range} and 
     * {@code room}, empty List if there are none.
     */
    List<Booking> getBookingsInRange(DateRange range, Room room);

    /**
     * Find users who have some room reserved in a certain time range.
     * @author Martin Palenik
     *
     * @param range time range
     * @throws IllegalArgumentException any argument is null
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return list of users who reserved a room inside the given timespan, empty otherwise
     */
    List<User> listReserved(DateRange range);
}
