package cz.muni.fi.pa165.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.api.dto.BookingCreateDTO;
import cz.muni.fi.pa165.api.dto.BookingDTO;
import cz.muni.fi.pa165.api.facade.BookingFacade;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.AdminService;
import cz.muni.fi.pa165.service.BookingDiscountService;
import cz.muni.fi.pa165.service.BookingService;
import cz.muni.fi.pa165.service.RoomService;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.service.auxiliary.BeanMappingService;
import javax.inject.Inject;

/**
 * @author Petr Valenta
 */
@Service
@Transactional
public class BookingFacadeImpl implements BookingFacade {

    @Inject
    private BookingService bookingService;

    @Inject
    private AdminService adminService;

    @Inject
    private RoomService roomService;
    
    @Inject
    private UserService userService;

    @Inject
    private BookingDiscountService bookingDiscountService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public List<BookingDTO> getAllBookings() {
        return beanMappingService.mapTo(bookingService.getAll(), BookingDTO.class);
    }

    @Override
    public List<BookingDTO> findBookingsByRange(DateRange range, Long roomId) {
        Room room = roomService.findById(roomId);
        return beanMappingService.mapTo(adminService.getBookingsInRange(range, room), BookingDTO.class);
    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = bookingService.findById(id);
        bookingService.cancel(booking);
    }

    @Override
    public BigDecimal calculateDiscount(BookingDTO booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null.");
        }
        return bookingDiscountService.calculateDiscount(beanMappingService.mapTo(booking, Booking.class));
    }

    @Override
    public void createBooking(BookingCreateDTO bookingCreate) {
        if(bookingCreate == null) {
            throw new IllegalArgumentException("Booking create DTO cannot be null.");
        }
        bookingService.book(beanMappingService.mapTo(bookingCreate, Booking.class));
    }

	@Override
	public void updateBooking(Long id) {
        Booking booking = bookingService.findById(id);
        bookingService.update(booking);
	}

	@Override
	public BookingDTO getBooking(Long id) {
        Booking booking = bookingService.findById(id);
        return (booking == null) ? null : beanMappingService.mapTo(booking, BookingDTO.class);
	}

	@Override
	public List<BookingDTO> findBookingsByRangeByUser(DateRange range, Long userId) {
        User user = userService.findById(userId);
        return beanMappingService.mapTo(adminService.getBookingsInRangeByUser(range, user), BookingDTO.class);
	}
    
}
