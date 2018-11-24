
// @author Martin Palenik

package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface BookingService {

    /**
     * Create a booking.
     *
     * @param user real person that booked the room
     * @param room booked room
     * @param range time interval when the room is being booked
     * @throws IllegalArgumentException any argument is null
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return true on success, false on failure
     */
    boolean bookRoom(User user, Room room, DateRange range);

    /**
     * Find users who have some room reserved in a certain time range.
     *
     * @param range time range
     * @throws IllegalArgumentException any argument is null
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return list of users, empty list if no users match the given criteria
     */
    List<User> listReserved(DateRange range);

    /**
     * Calculate the accommodation price.
     *
     * @param booking booking of given user in given time range
     * @throws IllegalArgumentException any argument is null
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return total accomodation price, zero otherwise
     */
    BigDecimal checkout(Booking booking);

    /**
     * List available rooms in the given hotel.
     *
     * @param hotel given hotel
     * @param range given time interval
     * @throws IllegalArgumentException any argument is null
     * @throws BookingManagerDataAccessException any exception on the DAO layer occurs
     * @return list of rooms, empty list if no rooms match the given criteria
     */
    List<Room> getAvailableRooms(Hotel hotel, DateRange range);

 }