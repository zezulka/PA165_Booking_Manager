package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Hotel;
import java.util.List;
import javax.validation.ConstraintViolationException;

/**
 *
 * Interface for CRUD operations for entity hotel.
 *
 * @author Soňa Barteková
 *
 */
public interface HotelDao {

    /**
     * Persists new hotel into database.
     *
     * @param h represents entity to be persisted into database.
     * @throws IllegalArgumentException h is null or the id is not null
     * @throws ConstraintViolationException any column constraints are violated
     */
    public void create(Hotel h);

    /**
     * Remove hotel entity from database.
     *
     * @param h represents hotel to be removed from database.
     * @throws IllegalArgumentException if hotel for removing is not stored.
     */
    public void remove(Hotel h);

    /**
     * Update persisted entity in the database.
     *
     * @param h represents persisted entity to be updated.
     * @throws IllegalArgumentException if hotel for updating is not in the
     * database.
     * @throws ConstraintViolationException any column constraints are violated
     * @return updated hotel entity.
     */
    public Hotel update(Hotel h);

    /**
     * Return the hotel entity with specific id.
     *
     * @param id represents id of the hotel entity.
     * @throws IllegalArgumentException id is null
     * @return the hotel entity associated with the given id.
     */
    public Hotel findById(Long id);

    /**
     * Return all hotels in the database.
     *
     * @return all hotel entities from the database.
     */
    public List<Hotel> findAll();

    /**
     * Return hotel with given name.
     *
     * @param name represents name of searched hotel.
     * @return hotel from database with given name, null if it does not exist.
     * @throws IllegalArgumentException if name is null.
     */
    public Hotel findByName(String name);
}
