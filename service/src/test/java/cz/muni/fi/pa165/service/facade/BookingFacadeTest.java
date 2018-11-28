package cz.muni.fi.pa165.service.facade;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

        hotel.setAddress("In The Middle of Nowhere");
        hotel.setName("Noname");
        
        rDTO.setDescription("Single room, beautiful view.");
        rDTO.setHotel(hDTO);
        rDTO.setImage(new byte[0]);
        rDTO.setNumber(101);
        rDTO.setRecommendedPrice(new BigDecimal("2000.0"));
        rDTO.setType(RoomType.SINGLE_ROOM);

        room.setHotel(hotel);
        room.setImage(new byte[0]);
        room.setNumber(101);
        room.setRecommendedPrice(new BigDecimal("2000.0"));
        room.setType(RoomType.SINGLE_ROOM);

        hDTO.setAddress("In The Middle Of Nowhere");
        hDTO.setName("Noname");

        bDTO.setId(1l);
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
    public void testBookingsByRange() {

    }

}
