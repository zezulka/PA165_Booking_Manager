package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.User;

import java.util.List;

import javax.validation.ConstraintViolationException;

/**
 * @author Miloslav Zezulka
 */
public interface BookingDao {

    /**
     * Creates a new {@link Booking}. The argument passed must not be null and
     * its id must be set to null.
     *
     * @throws IllegalArgumentException booking is null or the id is not null
     * @throws ConstraintViolationException any column constraints are violated
     * @param booking new booking to be inserted
     */
    public void create(Booking booking);

    /**
     * Removes an existing {@link Booking}. The argument passed must not be null
     * and its id must be different from null.
     *
     * @throws IllegalArgumentException booking is null or the id is null
     * @param booking booking to be removed
     */
    public void remove(Booking booking);

    /**
     * Updates an existing {@link Booking} and returns the instance relevant to
     * the current persistence context.
     *
     * @param booking booking to be updated in the database, cannot be null
     * @throws IllegalArgumentException booking is null or its id is null
     * @throws ConstraintViolationException any column constraints are violated
     * @return the managed instance returned from the database
     */
    public Booking update(Booking booking);

    /**
     * Returns a {@link Booking} with the id {@code id}. If no such booking is
     * present in the database, null is returned instead.
     *
     * @param id non-null id
     * @throws IllegalArgumentException id is null
     * @return booking with the id {@code id} or null
     */
    public Booking findById(Long id);

    /**
     * Returns all bookings present in the database.
     *
     * @return A {@link List} of all bookings, an empty {@link List} if there
     * are none.
     */
    public List<Booking> findAll();

    /**
     * Returns all bookings which were booked for the given Room. This includes
     * all the bookings done in the past.
     *
     * @param room room to search bookings with, cannot be null
     * @throws IllegalArgumentException room is null or the id of the room is
     * null
     * @return A {@link List} of all bookings submitted for the
     * {@code room}.
     */
    public List<Booking> findByRoom(Room room);

    /**
     * Returns all bookings made by given user.
     *
     * @param user user to search bookings with, cannot be null
     * @throws IllegalArgumentException user is null or the id of the user is
     * null
     * @return A {@link List} of all bookings submitted for the
     * {@code user}.
     */
    public List<Booking> findByUser(User user);
}
