package cz.muni.fi.pa165.service;

import org.mockito.cglib.core.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;

/**
 * @author Petr Valenta
 */
@Service
public class BookingDiscountServiceImpl implements BookingDiscountService {
    @Autowired
    private BookingDao bookingDao;

    @Override
    public List<Booking> bookingsForUser(final User user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");
        try {
            return bookingDao.findByUser(user);
        } catch (IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Error during service.", e);
        }
    }

    @Override
    public List<Booking> getPastBookings(final List<Booking> input) {
        List<Booking> output = new ArrayList<>();
        LocalDate now = LocalDate.now();

        for (Booking booking : input){
            if (booking.getTo().isBefore(now)) output.add(booking);
        }
        return output;
    }

    @Override
    public boolean isUserEligibleForDiscount(final DiscountType type, final User user) {
        if (type == null) throw new IllegalArgumentException("type cannot be null");
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        List<Booking> bookings = getPastBookings(bookingsForUser(user));


        switch (type){
            case REPEAT_CUSTOMER:
                if (bookings.size() > 5) //TODO add config file
                    return true;
                break;

            case RECENTLY_ACTIVE_CUSTOMER:
                LocalDate now = LocalDate.now();
                if (bookings.get(bookings.size()).getTo() // TODO will this fetch the latest booking?
                            .isAfter(now.minusDays(5))) //TODO add config file
                    return true;
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public BigDecimal calculateDiscount(final Booking booking) {
        return null;
    }
}
