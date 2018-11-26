package cz.muni.fi.pa165.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import javax.validation.ConstraintViolationException;

import cz.muni.fi.pa165.dao.HotelDao;
import cz.muni.fi.pa165.entity.Hotel;

import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;

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
        } catch (IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Error during service.", e);
        }
    }

    @Override
    public Hotel findByName(final String name) {
        if (name == null) throw new IllegalArgumentException("name cannot be null");
        if (name.isEmpty()) throw new IllegalArgumentException("name cannot be empty");
        try{
            return hotelDao.findByName(name);
        } catch (IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Error during service.", e);
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
        } catch (TransactionRequiredException | IllegalArgumentException | ConstraintViolationException | EntityExistsException e) {
            throw new BookingManagerDataAccessException("Error during service.", e);
        }
    }

    @Override
    public void delete(final Hotel hotel) {
        if (hotel == null) throw new IllegalArgumentException("hotel cannot be null");
        try {
            hotelDao.remove(hotel);
        } catch (TransactionRequiredException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Error during service.", e);
        }
    }

    @Override
    public void update(final Hotel hotel) {
        if (hotel == null) throw new IllegalArgumentException("hotel cannot be null");
        try {
            hotelDao.update(hotel);
        } catch (TransactionRequiredException | IllegalArgumentException | ConstraintViolationException e) {
            throw new BookingManagerDataAccessException("Error during service.", e);
        }
    }
}
