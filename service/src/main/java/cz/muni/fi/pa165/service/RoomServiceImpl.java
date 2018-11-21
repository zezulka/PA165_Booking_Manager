package cz.muni.fi.pa165.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.dao.RoomDao;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;

/**
 * 
 * @author Soňa Barteková
 *
 */

@Service
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	private RoomDao roomDao;

	@Override
	public List<Room> findAll() {
		return roomDao.findAll();
	}

	@Override
	public Room findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return roomDao.findById(id);
	}

	@Override
	public List<Room> findByHotel(Hotel hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
        }
        return roomDao.findByHotel(hotel);
	}

	@Override
	public Room findByNumber(Hotel hotel, Integer number) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
            }
        if (number == null) {
            throw new IllegalArgumentException("Number cannot be null.");
        }
		return roomDao.findByNumber(hotel, number);
	}

	@Override
	public boolean createRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        roomDao.create(room);
        if (roomDao.findById(room.getId()) != null) {
        	return true;
        }
		return false;
	}

	@Override
	public boolean deleteRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        roomDao.create(room);
        if (roomDao.findById(room.getId()) == null) {
        	return true;
        }
		return false;
	}

	@Override
	public Room updateRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
		return roomDao.update(room);
	}



}