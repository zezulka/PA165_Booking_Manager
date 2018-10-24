package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import java.util.List;

/**
 * @author Miloslav Zezulka
 */
public interface BookingDao {

    /**
     * Creates a new {@link Booking}. The argument passed must not be null
     * and its id must be set to null.
     * @throws IllegalArgumentException booking is null or the id is not null
     * @param booking new booking to be inserted
     */
    public void create(Booking booking);

    /**
     * Removes an existing {@link Booking}. The argument passed must not be null
     * and its id must be different from null.
     * @throws IllegalArgumentException booking is null or the id is null
     * @param booking booking to be removed
     */
    public void remove(Booking booking);

    /**
     * Returns a {@link Booking} with the id {@link id}. If no such 
     * {@link Booking} is present in the database, null is returned instead.
     * @param id non-null id
     * @throws IllegalArgumentException id is null
     * @return booking with the id {@link id} or null
     */
    public Booking findById(Long id);

    /**
     * Returns all {@link Booking}s present in the database.
     * @return A {@link List} of all Bookings, an empty List if there are none.
     */
    public List<Booking> findAll();

    /**
     * Returns all {@link Booking}s which were booked for the given Room. This
     * includes all the bookings done in the past.
     * @param room room to search bookings with
     * @return 
     */
    public List<Booking> findByRoom(Room room);
}
