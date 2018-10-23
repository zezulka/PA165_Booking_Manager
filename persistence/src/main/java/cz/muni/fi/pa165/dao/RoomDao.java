package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.Hotel;
import java.util.List;

/**
 *
 *
 */
public interface RoomDao {

    public void create(Room u);

    public Room findById(Long id);

    public Room findByNumber(Hotel hotel, Integer number);
    
    public List<Room> findByHotel(Hotel hotel);
    
    public List<Room> findAll();
}
