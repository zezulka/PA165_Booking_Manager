package cz.muni.fi.pa165.service;

import org.springframework.stereotype.Service;

import java.util.List;

import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import org.springframework.dao.DataAccessException;

/**
 * @author Petr Valenta
 */
@Service
public interface HotelService {

    /**
     * Finds a hotel by its id.
     *
     * @param id non-null id
     * @throws IllegalArgumentException {@code id} is null
     * @throws DataAccessException error during service
     * @return {@link Hotel} with the given {@code id} or null if such hotel does not exist
     */
    Hotel findById(Long id);

    /**
     * Finds a hotel by its name.
     *
     * @param name non empty String
     * @throws IllegalArgumentException {@code name} is null or empty
     * @throws DataAccessException error during service
     * @return {@link Hotel} instance with the given name or null if such hotel does not exist
     */
    Hotel findByName(String name);

    /**
     * Returns a list of all hotels.
     *
     * @throws DataAccessException error during service
     * @return {@link List} of all {@link Hotel} instances.
     */
    List<Hotel> findAll();

    /**
     * Creates a new {@link Hotel}.
     *
     * @param hotel to be created
     * @throws IllegalArgumentException {@code hotel} is null
     * @throws DataAccessException error during service
     */
    void create(Hotel hotel);

    /**
     * Deletes a {@link Hotel}.
     *
     * @param hotel {@link Hotel} to be removed
     * @throws IllegalArgumentException {@code hotel} is null
     * @throws DataAccessException error during service
     */
    void delete(Hotel hotel);

    /**
     * Updates a {@link Hotel}.
     *
     * @param hotel {@link Hotel} to be updated
     * @throws IllegalArgumentException {@code hotel} is null
     * @throws DataAccessException error during service
     */
    void update(Hotel hotel);

    /**
     * Adds a {@link Room}.
     *
     * @param hotel target {@link Hotel}
     * @param room {@link Room} to be added
     * @throws IllegalArgumentException {@code hotel} or {@code room} is null
     * @throws DataAccessException error during service
     */
    void addRoom(Hotel hotel, Room room);

//    /**
//     * Removes a {@link Room}.
//     *
//     * @param hotel target {@link Hotel}
//     * @param room {@link Room} to be removed
//     * @throws IllegalArgumentException {@code hotel} or {@code room} is null
//     * @throws DataAccessException error during service
//     */
//    void removeRoom(Hotel hotel, Room room);
}