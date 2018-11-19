package cz.muni.fi.pa165.service;

import org.springframework.stereotype.Service;

import java.util.List;

import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;

/**
 * @author Petr Valenta
 */
@Service
public interface HotelService {

    /**
     * Finds a hotel by its id.
     *
     * @param id non-null id
     * @throws IllegalArgumentException id is null
     * @throws BookingManagerDataAccessException error during service
     * @return {@link Hotel} with the given id or null if such hotel does not exist
     */
    Hotel findById(Long id);

    /**
     * Finds a hotel by its name.
     *
     * @param name non empty String
     * @throws IllegalArgumentException name is null or empty
     * @throws BookingManagerDataAccessException error during service
     * @return {@link Hotel} instance with the given name or null if such hotel does not exist
     */
    Hotel findByName(String name);

    /**
     * Returns a list of all hotels.
     *
     * @throws BookingManagerDataAccessException error during service
     * @return {@link List} of all {@link Hotel} instances.
     */
    List<Hotel> findAll();

    /**
     * Creates a new {@link Hotel}.
     *
     * @param hotel to be created
     * @throws IllegalArgumentException hotel is null
     * @throws BookingManagerDataAccessException error during service
     */
    void create(Hotel hotel);

//    /**
//     * Adds a {@link Room}.
//     *
//     * @param h target {@link Hotel}
//     * @param r {@link Room} to be added
//     * @throws IllegalArgumentException h/r is null or h/r has null id
//     */
//    void addRoom(Hotel hotel, Room room);
//
//    /**
//     * Removes a {@link Room}.
//     *
//     * @param h target {@link Hotel}
//     * @param r {@link Room} to be removed
//     * @throws IllegalArgumentException h/r is null or h/r has null id
//     */
//    void removeRoom(Hotel hotel, Room room);

    /**
     * Deletes a {@link Hotel}.
     *
     * @param hotel {@link Hotel} to be removed
     * @throws IllegalArgumentException hotel is null
     * @throws BookingManagerDataAccessException error during service
     */
    void delete(Hotel hotel);
}
