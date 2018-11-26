package cz.muni.fi.pa165.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.api.dto.BookingDTO;
import cz.muni.fi.pa165.api.facade.BookingFacade;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.service.AdminService;
import cz.muni.fi.pa165.service.BookingDiscountService;
import cz.muni.fi.pa165.service.BookingService;
import cz.muni.fi.pa165.service.RoomService;
import cz.muni.fi.pa165.service.auxiliary.BeanMappingService;

/**
 * @author Petr Valenta
 */
@Service
@Transactional
public class BookingFacadeImpl implements BookingFacade {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingDiscountService bookingDiscountService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public List<BookingDTO> getAllBookings(){
        return beanMappingService.mapTo(bookingService.getAll(), BookingDTO.class);
    }

    @Override
    public List<BookingDTO> findBookingsByRange(DateRange range, Long roomId){
        Room room = roomService.findById(roomId);
        return beanMappingService.mapTo(adminService.getBookingsInRange(range, room), BookingDTO.class);
    }

    @Override
    public void cancelBooking(Long id){
        Booking booking = bookingService.findById(id);
        bookingService.cancel(booking);
    }

    @Override
    public BigDecimal calculateDiscount(BookingDTO booking){
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null.");
        }
        return bookingDiscountService.calculateDiscount(beanMappingService.mapTo(booking, Booking.class));
    }
}

