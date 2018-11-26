package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.dao.RoomDao;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import static cz.muni.fi.pa165.api.utils.DateRangeUtils.rangesOverlap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Soňa Barteková
 *
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private BookingService bookingService;

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
    public void createRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        try {
            roomDao.create(room);
        } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not create a new room.", e);
        }
    }

    @Override
    public void deleteRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        try {
            roomDao.remove(room);
        } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not delete room.", e);
        }
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
        if (range == null) {
            throw new IllegalArgumentException("Range cannot be null.");
        }
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
        }
        try {
            return roomDao.findByHotel(hotel).stream()
                    .filter((room) -> isRoomAvailableInRange(room, range))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Could not find "
                    + "available rooms.", e);
        }
    }

    private boolean isRoomAvailableInRange(Room room, DateRange range) {
        return bookingService.findByRoom(room).stream()
                .filter((booking) -> {
                    DateRange bookingRange = new DateRange(booking.getFrom(),
                            booking.getTo());
                    return rangesOverlap(bookingRange, range);
                })
                .collect(Collectors.toList()).isEmpty();
    }

}
