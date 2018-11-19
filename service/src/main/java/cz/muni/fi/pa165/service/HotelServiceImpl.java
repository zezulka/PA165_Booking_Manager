package cz.muni.fi.pa165.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import cz.muni.fi.pa165.dao.HotelDao;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;

/**
 * @author  Petr Valenta
 */
@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelDao hotelDao;

    @Override
    public Hotel findById(final Long id) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");
        try{
            return hotelDao.findById(id);
        } catch (Exception e) {
            throw new DataAccessException("error during service: " + e){}; //DAE is abstract
        }
    }

    @Override
    public Hotel findByName(final String name) {
        if (name == null) throw new IllegalArgumentException("name cannot be null");
        if (name.isEmpty()) throw new IllegalArgumentException("name cannot be empty");
        try{
            return hotelDao.findByName(name);
        } catch (Exception e) {
            throw new DataAccessException("error during service: " + e){}; //DAE is abstract
        }
    }

    @Override
    public List<Hotel> findAll() {
        return hotelDao.findAll();
    }

    @Override
    public void create(final Hotel hotel) {
        if (hotel == null) throw new IllegalArgumentException("hotel cannot be null");
        try {
            hotelDao.create(hotel);
        } catch (Exception e) {
            throw new DataAccessException("error during service: " + e){}; //DAE is abstract
        }
    }

    @Override
    public void delete(final Hotel hotel) {
        if (hotel == null) throw new IllegalArgumentException("hotel cannot be null");
        try {
            hotelDao.remove(hotel);
        } catch (Exception e) {
            throw new DataAccessException("error during service: " + e){}; //DAE is abstract
        }
    }

    @Override
    public void update(final Hotel hotel) {
        if (hotel == null) throw new IllegalArgumentException("hotel cannot be null");
        try {
            hotelDao.update(hotel);
        } catch (Exception e) {
            throw new DataAccessException("error during service: " + e){}; //DAE is abstract
        }
    }

    @Override
    public void addRoom(final Hotel hotel, final Room room) {
        if (hotel == null) throw new IllegalArgumentException("hotel cannot be null");
        if (room == null) throw new IllegalArgumentException("room cannot be null");

        throw new DataAccessException("not yet implemented"){};
        //roomDao.findByHotel(hotel);
    }
}
