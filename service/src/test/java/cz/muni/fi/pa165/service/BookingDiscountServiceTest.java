package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.api.DiscountType;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.auxiliary.DateService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.utils.Security;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import org.dozer.inject.Inject;
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
 *
 * @author Miloslav Zezulka
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public final class BookingDiscountServiceTest {

    @Mock
    private BookingDao bookingDao;

    @Mock
    private DateService dateService;

    @Autowired
    @InjectMocks
    private BookingDiscountService bookingDiscountService;

    private Room room;
    private Hotel hotel;
    private Booking booking;
    private User user;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void simulateSystemDate() {
        LocalDate backToTheFuture = LocalDate.of(2015, Month.OCTOBER, 21);
        when(dateService.getCurrentDate()).thenReturn(backToTheFuture);
    }

    @Before
    public void entities() {
        room = new Room();
        hotel = new Hotel();
        user = new User();

        String password = "password";
        String hash = Security.createHash(password);

        user.setFirstName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@foo.bar");
        user.setAdmin(false);
        user.setPasswordHash(hash);

        room.setDescription("Single room, beautiful view.");
        room.setHotel(hotel);
        room.setImage(new byte[0]);
        room.setNumber(101);
        room.setRecommendedPrice(new BigDecimal("2000.0"));
        room.setType(RoomType.SINGLE_ROOM);

        hotel.setAddress("In The Middle Of Nowhere");
        hotel.setName("Noname");

        booking = new Booking();
        booking.setFromDate(LocalDate.of(2015, Month.OCTOBER, 22));
        booking.setToDate(LocalDate.of(2015, Month.OCTOBER, 30));
        booking.setRoom(room);
        booking.setTotal(new BigDecimal("1000.0"));
        booking.setUser(user);
    }

    @Test
    public void bookingsForUser() {
        when(bookingDao.findByUser(user)).thenReturn(Collections.singletonList(booking));
        assertThat(bookingDiscountService.bookingsForUser(user)).containsExactly(booking);
    }

    @Test
    public void bookingsForAnotherUser() {
        User usr = new User();

        String password = "password123";
        String hash = Security.createHash(password);

        usr.setFirstName("Joe");
        usr.setSurname("Black");
        usr.setEmail("joe.black@foo.bar");
        usr.setAdmin(false);
        usr.setPasswordHash(hash);
        when(bookingDao.findByUser(user)).thenReturn(Collections.singletonList(booking));
        when(bookingDao.findByUser(usr)).thenReturn(Collections.<Booking>emptyList());
        assertThat(bookingDiscountService.bookingsForUser(usr)).isEmpty();
    }

    @Test
    public void getPastBookings() {
        List<Booking> bookings = Collections.singletonList(booking);
        assertThat(bookingDiscountService.getPastBookings(bookings)).isEmpty();
        when(dateService.getCurrentDate()).thenReturn(LocalDate.of(2015, Month.NOVEMBER, 1));
        assertThat(bookingDiscountService.getPastBookings(bookings)).containsExactly(booking);
    }

    @Test
    public void isUserEligibleForDiscount() {
        when(bookingDao.findByUser(user)).thenReturn(Collections.singletonList(booking));
        when(dateService.getCurrentDate()).thenReturn(LocalDate.of(2015, Month.NOVEMBER, 1));
        assertThat(bookingDiscountService
                .isUserEligibleForDiscount(DiscountType.RECENTLY_ACTIVE_CUSTOMER, user))
                .isTrue();
    }

    @Test
    public void userMadeBookingButOnlyInFuture() {
        when(bookingDao.findByUser(user)).thenReturn(Collections.singletonList(booking));
        assertThat(bookingDiscountService
                .isUserEligibleForDiscount(DiscountType.RECENTLY_ACTIVE_CUSTOMER, user))
                .isFalse();
        assertThat(bookingDiscountService
                .isUserEligibleForDiscount(DiscountType.REPEAT_CUSTOMER, user))
                .isFalse();
    }

    @Test
    public void userMadeBookingLongTimeAgo() {
        when(bookingDao.findByUser(user)).thenReturn(Collections.singletonList(booking));
        when(dateService.getCurrentDate()).thenReturn(LocalDate.of(2016, Month.NOVEMBER, 1));
        assertThat(bookingDiscountService
                .isUserEligibleForDiscount(DiscountType.RECENTLY_ACTIVE_CUSTOMER, user))
                .isFalse();
    }

    @Test
    public void longTermCustomer() {
        Properties props = new Properties();
        props.setProperty("repeatCustomerBookings", "1");
        props.setProperty("recentlyActiveDays", "1");
        
        BookingDiscountServiceImpl castedService = (BookingDiscountServiceImpl) bookingDiscountService;
        castedService.setProperties(props);
        
        when(bookingDao.findByUser(user)).thenReturn(Collections.singletonList(booking));
        when(dateService.getCurrentDate()).thenReturn(LocalDate.of(2015, Month.NOVEMBER, 1));
        assertThat(bookingDiscountService
                .isUserEligibleForDiscount(DiscountType.REPEAT_CUSTOMER, user))
                .isTrue();
    }

}
