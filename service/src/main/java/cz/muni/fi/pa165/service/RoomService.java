package cz.muni.fi.pa165.service;

import java.util.List;
import org.springframework.stereotype.Service;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.Hotel;

/**
 * 
 * @author Soňa Barteková
 *
 */
@Service
public interface RoomService {
	
	/**
	 * Creates new room.
	 * 
	 * @param room room to be stored. Cannot be null.
	 * @throws IllegalArgumentException if room is null.
	 * @return true if room successfully stored, false otherwise.
	 */
	boolean createRoom(Room room);
	
	/**
	 * Delete given room.
	 * 
	 * @param room room to be deleted from database. Cannot be null.
	 * @throws IllegalArgumentException if room is null.
	 * @return true if room successfully deleted, false otherwise.
	 */
	boolean deleteRoom(Room room);
	
	/**
	 * Get all rooms.
	 * 
	 * @return List of rooms. Empty List if there are none.
	 */
	List<Room> findAll();
	
	/**
	 * Finds room by its database identifier.
	 * 
	 * @param id Room identifier. Must not be null.
	 * @throws IllegalArgumentException if id is null.
	 * @return Room with given identifier. Null if there is no such room.
	 */
	Room findById(Long id);
	
	/**
	 * Get all rooms in the given hotel.
	 * 
	 * @param hotel given Hotel. Cannot by null.
	 * @throws IllegalArgumentException if hotel is null.
	 * @return List of rooms in given hotel.
	 */
	List<Room> findByHotel(Hotel hotel);
	
	/**
	 * Get room with given number of given hotel.
	 * 
	 * @param hotel hotel where the room is located. Cannot be null.
	 * @param number number of the room. Cannot be null.
	 * @throws IllegalArgumentException if hotel is null or number is null.
	 * @return Room with given number. Null if there is no such
	 * room with this number in given hotel.
	 */
	Room findByNumber(Hotel hotel, Integer number);
	
}
