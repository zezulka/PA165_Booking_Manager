package cz.muni.fi.pa165.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.dao.RoomDao;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;

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
        try {
            return roomDao.findById(id);
        } catch (IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not find room by id.", e);
        }
	}

	@Override
	public List<Room> findByHotel(Hotel hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
        }
        try {
        	return roomDao.findByHotel(hotel);
        } catch (IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not find room by hotel.", e);
        }
	}

	@Override
	public Room findByNumber(Hotel hotel, Integer number) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
            }
        if (number == null) {
            throw new IllegalArgumentException("Number cannot be null.");
        }
        try {
        	return roomDao.findByNumber(hotel, number);
		} catch (IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not find room by number.", e);
        }
	}

	@Override
	public boolean createRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        try {
            roomDao.create(room);
        } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not create a new room.", e);
        }
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
        try {
            roomDao.remove(room);
        } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not delete room.", e);
        }
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
        try {
    		return roomDao.update(room);
        } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not update room.", e);
        }
	}

    @Override
    public List<Room> getAvailableRooms(DateRange range, Hotel hotel) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
