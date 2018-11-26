package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.auxiliary.DateService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import static org.assertj.core.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Miloslav Zezulka
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public final class BookingServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    BookingDao bookingDao;

    @Mock
    DateService dateService;

    @Autowired
    @InjectMocks
    BookingService bookingService;

    private Room defaultRoom;
    private Hotel defaultHotel;
    private Booking defaultBooking;
    private User defaultUser;

    @BeforeMethod
    public void simulateSystemDate() {
        LocalDate backToTheFuture = LocalDate.of(2015, Month.OCTOBER, 21);
        when(dateService.getCurrentDate()).thenReturn(backToTheFuture);
    }

    @BeforeMethod
    public void entities() {
        defaultRoom = new Room();
        defaultHotel = new Hotel();

        defaultRoom.setDescription("Single room, beautiful view.");
        defaultRoom.setHotel(defaultHotel);
        defaultRoom.setImage(new byte[0]);
        defaultRoom.setNumber(101);
        defaultRoom.setRecommendedPrice(new BigDecimal("2000.0"));
        defaultRoom.setType(RoomType.SINGLE_ROOM);

        defaultHotel.setAddress("In The Middle Of Nowhere");
        defaultHotel.setName("Noname");

        defaultBooking = new Booking();
        defaultBooking.setFrom(LocalDate.of(2015, Month.OCTOBER, 22));
        defaultBooking.setTo(LocalDate.of(2015, Month.OCTOBER, 30));
        defaultBooking.setRoom(defaultRoom);
        defaultBooking.setTotal(new BigDecimal("1000.0"));
        defaultBooking.setUser(defaultUser);
    }

    @BeforeMethod
    private void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void bookNull() {
        assertThatThrownBy(() -> bookingService.book(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void bookHappyScenario() {
        when(bookingDao.findAll()).thenReturn(Collections.singletonList(defaultBooking));
        bookingService.book(defaultBooking);
        verify(bookingDao).create(defaultBooking);
        assertThat(bookingService.getAll()).containsExactly(defaultBooking);
    }
    
    @Test
    public void bookTwoDifferentInSameTime() {
        bookingService.book(defaultBooking);
        Room r1 = new Room();
        r1.setDescription("Double room, marvelous view.");
        r1.setHotel(defaultHotel);
        r1.setImage(new byte[0]);
        r1.setNumber(111);
        r1.setRecommendedPrice(new BigDecimal("2000.0"));
        r1.setType(RoomType.DOUBLE_ROOM);
        
        Booking b1 = new Booking();
        b1.setFrom(LocalDate.of(2015, Month.OCTOBER, 22));
        b1.setTo(LocalDate.of(2015, Month.OCTOBER, 30));
        b1.setRoom(r1);
        b1.setUser(defaultUser);
        bookingService.book(b1);
    }
    
    @Test
    public void bookTwoDifferentInDifferentTime() {
        bookingService.book(defaultBooking);
        Room r1 = new Room();
        r1.setDescription("Double room, marvelous view.");
        r1.setHotel(defaultHotel);
        r1.setImage(new byte[0]);
        r1.setNumber(111);
        r1.setRecommendedPrice(new BigDecimal("2000.0"));
        r1.setType(RoomType.DOUBLE_ROOM);
        
        Booking b1 = new Booking();
        b1.setFrom(LocalDate.of(2016, Month.JANUARY, 1));
        b1.setTo(LocalDate.of(2016, Month.JANUARY, 10));
        b1.setRoom(r1);
        b1.setUser(defaultUser);
        bookingService.book(b1);
    }
    
    @Test
    public void bookTwice() {
        bookingService.book(defaultBooking);
        when(bookingDao.findByRoom(defaultBooking.getRoom()))
                .thenReturn(Collections.singletonList(defaultBooking));
        assertThatThrownBy(() -> bookingService.book(defaultBooking))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void bookOverlapping() {
        Booking b2 = new Booking();
        b2.setFrom(LocalDate.of(2015, Month.OCTOBER, 22));
        b2.setTo(LocalDate.of(2015, Month.OCTOBER, 31));
        b2.setRoom(defaultRoom);
        b2.setUser(defaultUser);
        bookingService.book(defaultBooking);
        when(bookingDao.findByRoom(defaultBooking.getRoom()))
                .thenReturn(Collections.singletonList(defaultBooking));
        assertThatThrownBy(() -> bookingService.book(b2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void bookOverlapping2() {
        Booking b1 = new Booking();
        b1.setFrom(LocalDate.of(2016, Month.JANUARY, 1));
        b1.setTo(LocalDate.of(2016, Month.JANUARY, 10));
        b1.setRoom(defaultRoom);
        b1.setUser(defaultUser);
        Booking b2 = new Booking();
        b2.setFrom(LocalDate.of(2015, Month.DECEMBER, 28));
        b2.setTo(LocalDate.of(2016, Month.JANUARY, 5));
        b2.setRoom(defaultRoom);
        b2.setUser(defaultUser);
        bookingService.book(b1);
        when(bookingDao.findByRoom(defaultBooking.getRoom()))
                .thenReturn(Collections.singletonList(b1));
        assertThatThrownBy(() -> bookingService.book(b2))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void bookInThePast() {
        Booking inThePast = new Booking();
        inThePast.setFrom(LocalDate.of(2015, Month.OCTOBER, 15));
        inThePast.setTo(LocalDate.of(2015, Month.OCTOBER, 20));
        inThePast.setRoom(defaultRoom);
        inThePast.setUser(defaultUser);
        assertThatThrownBy(() -> bookingService.book(inThePast))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    public void cancelHappyScenario() {
        bookingService.book(defaultBooking);
        bookingService.cancel(defaultBooking);
        verify(bookingDao).remove(defaultBooking);
    }
    
    @Test
    public void cancelTwice() {
        bookingService.book(defaultBooking);
        when(bookingDao.findByRoom(defaultBooking.getRoom()))
                .thenReturn(Collections.singletonList(defaultBooking));
        bookingService.cancel(defaultBooking);
        when(bookingDao.findByRoom(defaultBooking.getRoom()))
                .thenReturn(Collections.EMPTY_LIST);
        assertThatThrownBy(() -> bookingService.cancel(defaultBooking))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void cancelNull() {
        assertThatThrownBy(() -> bookingService.cancel(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    public void cancelOrderInThePast() {
        bookingService.book(defaultBooking);
        // Fast forward to future and see what happens
        when(dateService.getCurrentDate())
                .thenReturn(LocalDate.of(2016, Month.JANUARY, 1));
        assertThatThrownBy(() -> bookingService.cancel(defaultBooking))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void findByNullId() {
        assertThatThrownBy(() -> bookingService.findById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void findByIdHappyScenario() {
        when(bookingDao.findById(1L)).thenReturn(defaultBooking);
        bookingService.book(defaultBooking);
        assertThat(bookingService.findById(1L))
                .isEqualToComparingFieldByFieldRecursively(defaultBooking);
    }

    @Test
    public void getAllEmptyDb() {
        assertThat(bookingService.getAll()).isEmpty();
    }

    @Test
    public void getTotalPriceNull() {
        assertThatThrownBy(() -> bookingService.getTotalPrice(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void getTotalPriceHappyScenario() {
        when(bookingDao.findById(1L)).thenReturn(defaultBooking);
        // Let's simulate id generation for this given task
        Mockito.doAnswer((o) -> {
            Booking b = o.getArgumentAt(0, Booking.class);
            b.setId(1L);
            return null;
        }).when(bookingDao).create(defaultBooking);
        bookingService.book(defaultBooking);
        defaultBooking.setId(1L);
        assertThat(bookingService.getTotalPrice(defaultBooking))
                .isEqualToComparingFieldByFieldRecursively(new BigDecimal("1000.0"));
    }
}
