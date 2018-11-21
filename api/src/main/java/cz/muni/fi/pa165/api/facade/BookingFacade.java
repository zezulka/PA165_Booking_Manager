package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.BookingDTO;
import cz.muni.fi.pa165.service.DateRange;
import cz.muni.fi.pa165.service.DiscountType;
import java.util.List;

public interface BookingFacade {

    List<BookingDTO> getAllBookings();

    List<BookingDTO> findBookingsByRange(DateRange range, Long roomId);

    void cancelBooking(Long id);

    void discountBooking(DiscountType type, BookingDTO booking);
}
