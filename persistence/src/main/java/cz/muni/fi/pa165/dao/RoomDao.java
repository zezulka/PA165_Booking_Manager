package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.Hotel;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Martin Páleník
 */
public interface RoomDao {

    /**
     * Creates a new {@link Room}. The argument passed must not be null
     * and its id must be set to null.
     * @throws IllegalArgumentException room is null or the id is not null
     * @throws ConstraintViolationException any column constraints are violated
     * @param room new room to be inserted
     */
    public void create(Room room);

    /**
     * Removes an existing {@link Room}. The argument passed must not be null
     * and its id must be different from null.
     * @throws IllegalArgumentException room is null or the id is null
     * @param room room to be removed
     */
    public void remove(Room room);

    /**
     * Updates an existing {@link Room} and returns the instance relevant
     * to the current persistence context.
     * @param room room to be updated in the database, cannot be null
     * @throws IllegalArgumentException room is null or its id is null
     * @throws ConstraintViolationException any column constraints are violated
     * @return the managed instance returned from the database
     */
    public Room update(Room room);

    /**
     * Returns a {@link Room} with the id {@link id}. If no such room is
     * present in the database, null is returned instead.
     * @param id non-null id
     * @throws IllegalArgumentException id is null
     * @return room with the id {@link id} or null
     */
    public Room findById(Long id);

    /**
     * Returns all rooms with given number in the given hotel.
     * @param hotel hotel where the room is located
     * @param number number of the room in the hotel
     * @throws IllegalArgumentException hotel, number or its ids are null
     * @return A room in the given hotel
     */
    public Room findByNumber(Hotel hotel, Integer number);

    /**
     * Returns all rooms in the given hotel.
     * @param hotel the given hotel
     * @throws IllegalArgumentException hotel or its id is null
     * @return A {@link List} of all rooms in the given hotel
     */
    public List<Room> findByHotel(Hotel hotel);

    /**
     * Returns all rooms present in the database.
     * @return A {@link List} of all rooms, an empty {@link List} if there
     * are none.
     */
    public List<Room> findAll();
}