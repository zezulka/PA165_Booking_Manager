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

import static org.assertj.core.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * @author Petr Valenta
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public final class RoomServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private RoomDao roomDao;
    
    @Mock
    private BookingService bookingService;

    @Mock
    private HotelDao hotelDao;

    @Autowired
    @InjectMocks
    private RoomService roomService;

    @BeforeMethod
    private void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    private Room r1;
    private Hotel h1;
    private List<Room> l1;

    @BeforeMethod
    public void init(){
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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createRoomNull() {
        roomService.createRoom(null);
    }

    @Test
    public void deleteRoomHappy() {
        roomService.deleteRoom(r1);
        verify(roomDao).remove(r1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteRoomNull() {
        roomService.deleteRoom(null);
    }

    @Test
    public void updateRoomHappy() {
        roomService.updateRoom(r1);
        verify(roomDao).update(r1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateRoomNull() {
        roomService.updateRoom(null);
    }

    @Test
    public void findAll() {
        when(roomDao.findAll()).thenReturn(l1);
        List<Room> tmp = roomService.findAll();
        verify(roomDao).findAll();
        Assert.assertEquals(tmp, l1);
    }

    @Test(enabled = false)
    public void findAllEmpty() {
        List<Room> tmp = new ArrayList<>();
        Assert.assertTrue(tmp.isEmpty());

        when(roomDao.findAll()).thenReturn(tmp);
        tmp = roomService.findAll();
        verify(roomDao).findAll();
        Assert.assertEquals(tmp, l1);
    }

    @Test
    public void findByIdHappy() {
        when(roomDao.findById(1L)).thenReturn(r1);
        Room tmp = roomService.findById(1L);
        verify(roomDao).findById(1L);
        Assert.assertEquals(tmp, r1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdNull() {
        roomService.findById(null);
    }

    @Test
    public void findByHotelHappy() {
        when(roomDao.findByHotel(h1)).thenReturn(l1);
        List<Room> tmp = roomService.findByHotel(h1);
        verify(roomDao).findByHotel(h1);
        Assert.assertEquals(tmp, l1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByHotelNull() {
        roomService.findByHotel(null);
    }

    @Test
    public void findByNumberHappy() {
        when(roomDao.findByNumber(h1,1)).thenReturn(r1);
        Room tmp = roomService.findByNumber(h1, 1);
        verify(roomDao).findByNumber(h1, 1);
        Assert.assertEquals(tmp, r1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNumberNullHotel() {
        roomService.findByNumber(null, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNumberNullNumber() {
        roomService.findByNumber(h1, null);
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
        b.setFrom(LocalDate.of(2015, Month.OCTOBER, 21));
        b.setTo(LocalDate.of(2015, Month.OCTOBER, 30));
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
        b.setFrom(LocalDate.of(2015, Month.OCTOBER, 21));
        b.setTo(LocalDate.of(2015, Month.OCTOBER, 30));
        b.setRoom(r1);
        b.setUser(u);
        bookingService.book(b);
        assertThat(roomService.getAvailableRooms(range, r1.getHotel())).isEmpty();
    }
}