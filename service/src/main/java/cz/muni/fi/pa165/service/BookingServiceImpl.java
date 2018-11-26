package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
