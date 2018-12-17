package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.api.dto.BookingCreateDTO;
import cz.muni.fi.pa165.api.dto.BookingDTO;
import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.dto.RoomDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.api.facade.BookingFacade;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.Hotel;

import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.AdminService;
import cz.muni.fi.pa165.service.BookingDiscountService;
import cz.muni.fi.pa165.service.BookingService;
import cz.muni.fi.pa165.service.RoomService;
import cz.muni.fi.pa165.service.auxiliary.BeanMappingService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.argThat;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Soňa Barteková
 *
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BookingFacadeTest {

    @Inject
    @Spy
    private BeanMappingService beanMappingService;

    @Mock
    private AdminService adminService;

    @Mock
    private RoomService roomService;

    @Mock
    private BookingDiscountService bookingDiscountService;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingFacade bookingFacade = new BookingFacadeImpl();

    private RoomDTO rDTO;
    private Room room;
    private HotelDTO hDTO;
    private Hotel hotel;
    private BookingDTO bDTO;
    private Booking booking;
    private UserDTO uDTO;
    private User user;
    private BookingCreateDTO bCreateDTO;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        rDTO = new RoomDTO();
        hDTO = new HotelDTO();
        bDTO = new BookingDTO();
        uDTO = new UserDTO();
        booking = new Booking();
        room = new Room();
        hotel = new Hotel();
        user = new User();
        bCreateDTO = new BookingCreateDTO();

        hotel.setAddress("In The Middle of Nowhere");
        hotel.setName("Noname");

        rDTO.setDescription("Single room, beautiful view.");
        rDTO.setHotel(hDTO);
        rDTO.setImage(new byte[0]);
        rDTO.setNumber(101);
        rDTO.setId(1L);
        rDTO.setRecommendedPrice(new BigDecimal("2000.0"));
        rDTO.setType(RoomType.SINGLE_ROOM);

        room.setHotel(hotel);
        room.setImage(new byte[0]);
        room.setNumber(101);
        room.setRecommendedPrice(new BigDecimal("2000.0"));
        room.setType(RoomType.SINGLE_ROOM);
        room.setId(1L);

        hDTO.setAddress("In The Middle Of Nowhere");
        hDTO.setName("Noname");

        bDTO.setId(1L);
        bDTO.setFromDate(LocalDate.of(2015, Month.OCTOBER, 22));
        bDTO.setToDate(LocalDate.of(2015, Month.OCTOBER, 30));
        bDTO.setRoom(rDTO);
        bDTO.setTotal(new BigDecimal("1000.0"));
        bDTO.setUser(uDTO);

        booking.setFromDate(LocalDate.of(2015, Month.OCTOBER, 22));
        booking.setToDate(LocalDate.of(2015, Month.OCTOBER, 30));
        booking.setRoom(room);
        booking.setUser(user);
        booking.setTotal(new BigDecimal("1000.0"));
        booking.setId(1L);

        bCreateDTO.setFromDate(LocalDate.of(2015, Month.OCTOBER, 22));
        bCreateDTO.setToDate(LocalDate.of(2015, Month.OCTOBER, 30));
        bCreateDTO.setRoom(rDTO);
        bCreateDTO.setUsr(uDTO);
        bCreateDTO.setTotal(new BigDecimal("1000.0"));
    }

    @Test
    public void testGetAllBookingsTest() {
        when(bookingService.getAll())
                .thenReturn(Collections.singletonList(booking));
        List<BookingDTO> result = bookingFacade.getAllBookings();
        verify(bookingService).getAll();
        assertThat(result).containsExactly(bDTO);
    }

    @Test
    public void testBookingsByRangeFuture() {
        DateRange range = new DateRange(LocalDate.of(2016, Month.JANUARY, 1),
                LocalDate.of(2016, Month.JANUARY, 2));
        when(adminService.getBookingsInRange(range, room))
                .thenReturn(Collections.singletonList(booking));
        List<BookingDTO> result = bookingFacade.findBookingsByRange(range, 1L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testBookingsByRangeExpectOneBooking() {
        DateRange range = new DateRange(LocalDate.of(2015, Month.OCTOBER, 22),
                LocalDate.of(2015, Month.OCTOBER, 30));
        when(roomService.findById(room.getId())).thenReturn(room);
        when(adminService.getBookingsInRange(range, room))
                .thenReturn(Collections.singletonList(booking));
        List<BookingDTO> result = bookingFacade.findBookingsByRange(range, 1L);
        assertThat(result).containsExactly(bDTO);
    }

    @Test
    public void createBooking() {
        bookingFacade.createBooking(bCreateDTO);
        verify(bookingService).book(argThat(bookingCreateArgMatcher()));
    }

    @Test
    public void cancelBooking() {
        when(bookingService.findById(booking.getId())).thenReturn(booking);
        bookingFacade.cancelBooking(booking.getId());
        verify(bookingService).cancel(argThat(bookingArgMatcher()));
    }

    @Test
    public void discount() {
        bookingFacade.calculateDiscount(bDTO);
        verify(bookingDiscountService)
                .calculateDiscount(argThat(bookingArgMatcher()));
    }

    /**
     * As Dozer creates a new mapped instance of the given entity, we cannot
     * directly use such comparisons in tests:
     *
     * verify(bookingService).cancel(booking);
     *
     * Instead, parametrised ArgumentMatcher must be used.
     *
     * For the purposes of this test, comparing start and end date for a given
     * two bookings is enough since there are no bookings used in tests which
     * would have the same date range.
     */
    private ArgumentMatcher<Booking> bookingArgMatcher() {
        return (Booking b) -> bDTO.getFromDate().equals(b.getFromDate())
                && bDTO.getToDate().equals(b.getToDate());
    }

    private ArgumentMatcher<Booking> bookingCreateArgMatcher() {
        return (Booking b) -> bCreateDTO.getFromDate().equals(b.getFromDate())
                && bCreateDTO.getToDate().equals(b.getToDate());
    }
}
