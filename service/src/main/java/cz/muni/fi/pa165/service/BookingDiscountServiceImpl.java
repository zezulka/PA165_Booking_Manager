package cz.muni.fi.pa165.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cz.muni.fi.pa165.api.DiscountType;
import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.auxiliary.DateService;
import cz.muni.fi.pa165.service.exceptions.BookingManagerDataAccessException;
import cz.muni.fi.pa165.utils.PropertiesHelper;
import java.util.Iterator;

/**
 * @author Petr Valenta
 */
@Service
public class BookingDiscountServiceImpl implements BookingDiscountService {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private DateService dateService;

    private Properties properties;

    public BookingDiscountServiceImpl() {
        try {
            this.properties = new PropertiesHelper().getProperties();
        } catch (IOException e) {
            throw new BookingManagerDataAccessException("Error during service.", e);
        }
    }

    /**
     * For testing purposes only.
     */
    public void setProperties(Properties props) {
        this.properties = props;
    }

    @Override
    public List<Booking> bookingsForUser(final User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        try {
            return bookingDao.findByUser(user);
        } catch (IllegalArgumentException e) {
            throw new BookingManagerDataAccessException("Error during service.", e);
        }
    }

    @Override
    public List<Booking> getPastBookings(final List<Booking> input) {
        List<Booking> output = new ArrayList<>();
        LocalDate now = dateService.getCurrentDate();

        for (Booking booking : input) {
            if (booking.getTo().isBefore(now)) {
                output.add(booking);
            }
        }
        return output;
    }

    @Override
    public boolean isUserEligibleForDiscount(final DiscountType type, final User user) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        List<Booking> bookings = getPastBookings(bookingsForUser(user));
        if(bookings.isEmpty()) {
            return false;
        }

        switch (type) {
            case REPEAT_CUSTOMER:
                if (bookings.size() >= Integer.parseInt(properties.getProperty("repeatCustomerBookings"))) {
                    return true;
                }
                break;

            case RECENTLY_ACTIVE_CUSTOMER:
                LocalDate now = dateService.getCurrentDate();
                LocalDate latestBookingDate = getLatestBookingDate(bookings);
                if (latestBookingDate.isAfter(now.minusDays(Long.parseLong(
                        properties.getProperty("recentlyActiveDays"))))) {
                    return true;
                }
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public BigDecimal calculateDiscount(final Booking booking) {
        Properties props;
        try {
            PropertiesHelper propHelp = new PropertiesHelper();
            props = propHelp.getProperties();
        } catch (IOException e) {
            throw new BookingManagerDataAccessException("Error during service.", e);
        }

        int topDiscount = 0; // in percent

        for (DiscountType discount : DiscountType.values()) {
            int discountVal = Integer.parseInt(props.getProperty(discount.name()));
            if (discountVal > topDiscount) {
                if (isUserEligibleForDiscount(discount, booking.getUser())) {
                    topDiscount = discountVal;
                }
            }
        }

        // priceAfterDiscount = total * ( 100 - topDiscount )
        BigDecimal priceAfterDiscount = new BigDecimal("100");
        priceAfterDiscount.subtract(BigDecimal.valueOf(topDiscount));
        priceAfterDiscount.multiply(booking.getTotal());

        return priceAfterDiscount;
    }

    private LocalDate getLatestBookingDate(List<Booking> bookings) {
        if(bookings.isEmpty()) {
            throw new IllegalArgumentException("Cannot determine latest booking.");
        }
        Iterator<Booking> it = bookings.iterator();
        LocalDate candidate = it.next().getTo();
        while(it.hasNext()) {
            LocalDate nextCand = it.next().getTo();
            if(nextCand.isAfter(candidate)) {
                candidate = nextCand;
            }
        }
        return candidate;
    }
}
