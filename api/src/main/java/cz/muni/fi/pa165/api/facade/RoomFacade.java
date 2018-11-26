package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.dto.RoomCreateDTO;
import cz.muni.fi.pa165.api.dto.RoomDTO;
import java.util.List;

/**
 * @author Petr Valenta
 */
public interface RoomFacade {

    /**
     * Creates a room.
     * @param room to be created
     * @return true if created
     */
    void createRoom(RoomCreateDTO room);

    /**
     * Deletes a room.
     * @param room room to be deleted
     * @return true if deleted
     */
    void deleteRoom(RoomDTO room);

    /**
     * Returns a list of all rooms.
     * @return List of all rooms
     */
    List<RoomDTO> findAll();

    /**
     * Find a room by id.
     * @param id to search with
     * @return RoomDTO with the given id or null
     */
    RoomDTO findById(Long id);

    /**
     * Finds all rooms in a given hotel.
     * @param hotel
     * @return List of all rooms in the given hotel
     */
    List<RoomDTO> findByHotel(HotelDTO hotel);

    /**
     * Finds a room in a given hotel with specified number.
     * @param hotel
     * @param number
     * @return found room or null
     */
    RoomDTO findByNumber(HotelDTO hotel, Integer number);
}
