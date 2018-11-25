package cz.muni.fi.pa165.service;

import java.math.BigDecimal;
import java.util.List;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.User;

/**
 *
 * This service represents a discount which can be given
 * for a particular booking.
 * 
 * There are two possible scenarios when a discount will be applied:
 * 1 (recently active customer). The user has already made a certain amount of
 *     bookings during a past year (the amount will be a constant specified in
 *     configuration files).
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
     * @param input
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
     *
     * @param booking
     * @return
     */
    BigDecimal calculateDiscount(Booking booking);
}
