package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.service.auxiliary.DateService;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static cz.muni.fi.pa165.api.utils.DateRangeUtils.rangesOverlap;
import java.time.LocalDate;

/**
 *
 * @author Martin Palenik
 */
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private DateService dateService;

    @Override
    public void book(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("booking is null");
        }

        if (!booking.getFrom().isAfter(dateService.getCurrentDate())) {
            throw new IllegalArgumentException(
                    "trying to create booking in the past");
        }

        DateRange candidateRange = new DateRange(booking.getFrom(),
                booking.getTo());

        for (Booking b : bookingDao.findByRoom(booking.getRoom())) {
            DateRange nextRange = new DateRange(b.getFrom(), b.getTo());
            if (rangesOverlap(candidateRange, nextRange)) {
                throw new IllegalArgumentException("Booking overlaps with an "
                        + "already existing booking in the database.");
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
        LocalDate date = dateService.getCurrentDate();
        if (booking.getTo().isBefore(dateService.getCurrentDate())) {
            throw new IllegalArgumentException(
                    "Trying to cancel booking of a reservation that already passed.");
        }

        if (booking.getFrom().isBefore(dateService.getCurrentDate())) {
            throw new IllegalArgumentException("This booking is active now.");
        }

        if(!bookingDao.findByRoom(booking.getRoom()).contains(booking)) {
            throw new IllegalArgumentException("This booking does not exist in the database.");
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
        if (booking == null) {
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
