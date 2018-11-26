package cz.muni.fi.pa165.utils;

import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.service.DateRange;
import java.time.LocalDate;

/**
 *
 * @author Miloslav Zezulka
 */
public class DateRangeUtils {

    private DateRangeUtils() {
        
    }
    
    public static boolean isBookingInsideDateRange(Booking booking, DateRange range) {
        return !booking.getFrom().isBefore(range.getFromDate())
                && !booking.getTo().isAfter(range.getToDate());
    }

    public static DateRange rangeFromBooking(Booking booking) {
        return new DateRange(booking.getFrom(), booking.getTo());
    }
    
    public static boolean rangesOverlap(DateRange first, DateRange second) {
        LocalDate firstFrom = first.getFromDate();
        LocalDate firstTo = first.getToDate();
        LocalDate secondFrom = second.getFromDate();
        LocalDate secondTo = second.getToDate();
        if (firstFrom.equals(secondFrom) || firstTo.equals(secondTo)) {
            return true;
        }
        if (firstFrom.isBefore(secondFrom) && firstTo.isAfter(secondTo)) {
            return true;
        }
        if (secondFrom.isBefore(firstFrom) && secondTo.isAfter(firstTo)) {
            return true;
        }
        return (firstTo.isAfter(secondFrom) && firstTo.isBefore(secondTo))
                || (firstFrom.isBefore(secondTo) && firstFrom.isAfter(secondFrom));
    }
}
