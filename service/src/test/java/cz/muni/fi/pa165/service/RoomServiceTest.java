package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.dao.HotelDao;
import cz.muni.fi.pa165.dao.RoomDao;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * @author Petr Valenta
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public final class RoomServiceTest {

    @Mock
    private RoomDao roomDao;

    @Mock
    private BookingService bookingService;

    @Mock
    private HotelDao hotelDao;

    @Autowired
    @InjectMocks
    private RoomService roomService;

    private Room r1;
    private Hotel h1;
    private List<Room> l1;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        r1 = new Room();
        h1 = new Hotel();

        r1.setDescription("Single room, beautiful view.");
        r1.setHotel(h1);
        r1.setImage(new byte[0]);
        r1.setNumber(101);
        r1.setRecommendedPrice(new BigDecimal("2000.0"));
        r1.setType(RoomType.SINGLE_ROOM);

        h1.setAddress("In The Middle Of Nowhere");
        h1.setName("Noname");
        l1 = new ArrayList<>();
        l1.add(r1);
        h1.setRooms(l1);
    }

    @Test
    public void createRoomHappy() {
        roomService.createRoom(r1);
        verify(roomDao).create(r1);
    }

    @Test
    public void createRoomNull() {
        assertThatThrownBy(() -> roomService.createRoom(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteRoomHappy() {
        roomService.deleteRoom(r1);
        verify(roomDao).remove(r1);
    }

    @Test
    public void deleteRoomNull() {
        assertThatThrownBy(() -> roomService.deleteRoom(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateRoomHappy() {
        roomService.updateRoom(r1);
        verify(roomDao).update(r1);
    }

    @Test
    public void updateRoomNull() {
        assertThatThrownBy(() -> roomService.updateRoom(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findAll() {
        when(roomDao.findAll()).thenReturn(l1);
        List<Room> tmp = roomService.findAll();
        verify(roomDao).findAll();
        assertEquals(tmp, l1);
    }

    @Test
    public void findAllEmpty() {
        List<Room> tmp = new ArrayList<>();

        when(roomDao.findAll()).thenReturn(tmp);
        tmp = roomService.findAll();
        verify(roomDao).findAll();
        assertEquals(tmp, Collections.EMPTY_LIST);
    }

    @Test
    public void findByIdHappy() {
        when(roomDao.findById(1L)).thenReturn(r1);
        Room tmp = roomService.findById(1L);
        verify(roomDao).findById(1L);
        assertEquals(tmp, r1);
    }

    @Test
    public void findByIdNull() {
        assertThatThrownBy(() -> roomService.findById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByHotelHappy() {
        when(roomDao.findByHotel(h1)).thenReturn(l1);
        List<Room> tmp = roomService.findByHotel(h1);
        verify(roomDao).findByHotel(h1);
        assertEquals(tmp, l1);
    }

    @Test
    public void findByHotelNull() {
        assertThatThrownBy(() -> roomService.findByHotel(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByNumberHappy() {
        when(roomDao.findByNumber(h1, 1)).thenReturn(r1);
        Room tmp = roomService.findByNumber(h1, 1);
        verify(roomDao).findByNumber(h1, 1);
        assertEquals(tmp, r1);
    }

    @Test
    public void findByNumberNullHotel() {
        assertThatThrownBy(() -> roomService.findByNumber(null, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByNumberNullNumber() {
        assertThatThrownBy(() -> roomService.findByNumber(h1, null))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void getAvailableRooms() {
        DateRange range = new DateRange(LocalDate.of(2015, Month.OCTOBER, 21), LocalDate.of(2015, Month.OCTOBER, 22));
        roomService.createRoom(r1);
        Room r2 = new Room();
        r2.setDescription("Single room, horrible view but cheap.");
        r2.setHotel(h1);
        r2.setImage(new byte[0]);
        r2.setNumber(111);
        r2.setRecommendedPrice(new BigDecimal("300.0"));
        r2.setType(RoomType.SINGLE_ROOM);

        when(roomDao.findByHotel(h1)).thenReturn(Arrays.asList(r1, r2));

        roomService.createRoom(r2);
        assertThat(roomService.getAvailableRooms(range, h1))
                .containsExactly(r1, r2);
    }

    @Test
    public void createBookingExpectNoRoomAvailableRangeInside() {
        DateRange range = new DateRange(LocalDate.of(2015, Month.OCTOBER, 21), LocalDate.of(2015, Month.OCTOBER, 22));
        roomService.createRoom(r1);
        User u = new User();
        u.setEmail("foo@bar");
        u.setFirstName("John");
        u.setSurname("Doe");
        u.setPasswordHash("");
        Booking b = new Booking();
        b.setFromDate(LocalDate.of(2015, Month.OCTOBER, 21));
        b.setToDate(LocalDate.of(2015, Month.OCTOBER, 30));
        b.setRoom(r1);
        b.setUser(u);
        bookingService.book(b);
        assertThat(roomService.getAvailableRooms(range, r1.getHotel())).isEmpty();
    }

    @Test
    public void createBookingExpectNoRoomAvailableOverlapingRanges() {
        DateRange range = new DateRange(LocalDate.of(2015, Month.OCTOBER, 15), LocalDate.of(2015, Month.OCTOBER, 22));
        roomService.createRoom(r1);
        User u = new User();
        u.setEmail("foo@bar");
        u.setFirstName("John");
        u.setSurname("Doe");
        u.setPasswordHash("");
        Booking b = new Booking();
        b.setFromDate(LocalDate.of(2015, Month.OCTOBER, 21));
        b.setToDate(LocalDate.of(2015, Month.OCTOBER, 30));
        b.setRoom(r1);
        b.setUser(u);
        bookingService.book(b);
        assertThat(roomService.getAvailableRooms(range, r1.getHotel())).isEmpty();
    }
}
