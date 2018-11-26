package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.api.DiscountType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.User;

/**
 * @author  Petr Valenta
 *
 * This service represents a discount which can be given
 * for a particular booking.
 * 
 * There are two possible scenarios when a discount will be applied:
 *
 * 1 (recently active customer). The user had a booking in last x days
 *   (the amount of days will be a constant specified in configuration files).
 *
 * 2 (repeat customer). The user has already made a certain amount of bookings
 *     in total (again, the specific amount will be specified in configuration
 *     files).
 * 
 */
public interface BookingDiscountService {

    /**
     * Returns a list of all bookings made by selected user.
     * @param user
     * @return a list of bookings
     */
    List<Booking> bookingsForUser(User user);

    /**
     * Filters out future bookings.
     *
     * @param input list of bookings
     * @return a list of past bookings
     */
    List<Booking> getPastBookings(List<Booking> input);

    /**
     * Returns true if selected user is eligible for specific discount.
     * @param type
     * @param user
     * @return true if user is eligible, false otherwise
     */
    boolean isUserEligibleForDiscount(DiscountType type, User user);

    /**
     * Checks for eglible discounts and calculates price
     * @param booking
     * @return price after discount
     */
    BigDecimal calculateDiscount(Booking booking);
}
