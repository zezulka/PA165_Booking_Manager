package cz.muni.fi.pa165.service;

import java.math.BigDecimal;
import java.util.List;

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
    List<Booking> bookingsForUser(User user);
    boolean isUserEligibleForDiscount(User user);
    BigDecimal calculateDiscount(Booking booking);
}
