package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;

public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    private boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }

    @Override
    public void book(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("booking is null");
        }

        for (Booking existing : bookingDao.findByRoom(booking.getRoom())){
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
        if (booking == null){
            throw new IllegalArgumentException("booking cannot be null");
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
    public BigDecimal getTotalPrice(Booking booking) {
        // will be superseded by discount mechanism upon its completion
        // return Math.max(0, booking.getTotal() - calculateDiscount(booking));
        return bookingDao.findById(booking.getId()).getTotal();
    }
}
