package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;

import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 
 * @author Martin Palenik
 */
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    @Override
    public void book(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("booking is null");
        }

        if (!booking.getFrom().isAfter(LocalDate.now())) {
            throw new BookingManagerDataAccessException(
                    "trying to create booking in the past");
        }

        for (Booking existing : bookingDao.findByRoom(booking.getRoom())) {
            if (
                    booking.getFrom().isAfter(existing.getFrom())
                            &&
                            booking.getFrom().isBefore(existing.getTo())
            ) {
                throw new BookingManagerDataAccessException(
                        "booking start date collides with another booking");
            }

            if (
                    booking.getTo().isAfter(existing.getFrom())
                            &&
                            booking.getTo().isBefore(existing.getTo())
            ) {
                throw new BookingManagerDataAccessException(
                        "booking end date collides with another booking");
            }
        }

        try {
            bookingDao.create(booking);
        } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("BookingDAO could not create a new booking.", e);
        }
    }

    @Override
    public void cancel(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("booking cannot be null");
        }

        if (!booking.getTo().isBefore(LocalDate.now())) {
            throw new BookingManagerDataAccessException(
                    "trying to cancel booking of a reservation that already passed");
        }

        try {
            bookingDao.remove(booking);
        } catch (TransactionRequiredException | IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Error during service.", e);
        }
    }

    @Override
    public List<Booking> getAll() {
        return bookingDao.findAll();
    }

    @Override
    public Booking findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return bookingDao.findById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Booking booking) {
        if(booking == null) {
            throw new IllegalArgumentException("Booking cannot be null.");
        }
        // will be superseded by discount mechanism upon its completion
        // return Math.max(0, booking.getTotal() - calculateDiscount(booking));
        return bookingDao.findById(booking.getId()).getTotal();
    }

    @Override
    public List<Booking> findByRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room must not be null.");
        }
        return bookingDao.findByRoom(room);
    }
}
