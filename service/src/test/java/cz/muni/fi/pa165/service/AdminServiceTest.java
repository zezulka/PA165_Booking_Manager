package cz.muni.fi.pa165.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.auxiliary.DateService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.utils.Security;

/**
 * 
 * @author Soňa Barteková
 *
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public final class AdminServiceTest extends AbstractTransactionalTestNGSpringContextTests{

    @Mock
    BookingDao bookingDao;

    @Mock
    DateService dateService;

    @Autowired
    @InjectMocks
    AdminService adminService;
    
    @Autowired
    @InjectMocks
    BookingService bookingService;

    private Room room;
    private Hotel hotel;
    private Booking booking1, booking2, booking3;
    
    private User user;
    private User admin;
    private String password, hash;
    private DateRange dr;
    private List<Booking> bookings;

    @BeforeMethod
    public void simulateSystemDate() {
        LocalDate backToTheFuture = LocalDate.of(2015, Month.OCTOBER, 21);
        when(dateService.getCurrentDate()).thenReturn(backToTheFuture);
    }
   
    @BeforeMethod
    public void init() {        
    	user = new User();
	    admin = new User();
	    password = "password";
	    hash = Security.createHash(password);
	
	    user.setFirstName("Jozef");
	    user.setSurname("Neznamy");
	    user.setEmail("user@gmail.com");
	    user.setAdmin(false);
	    user.setPasswordHash(hash);
	
	    admin.setFirstName("Jozef");
	    admin.setSurname("NeznamyAdmin");
	    admin.setEmail("admin@gmail.com");
	    admin.setAdmin(true);
	    admin.setPasswordHash(hash);
	    
	    room = new Room();
	    hotel = new Hotel();

        room.setDescription("Single room, beautiful view.");
        room.setHotel(hotel);
        room.setImage(new byte[0]);
        room.setNumber(101);
        room.setRecommendedPrice(new BigDecimal("2000.0"));
        room.setType(RoomType.SINGLE_ROOM);

        hotel.setAddress("In The Middle Of Nowhere");
        hotel.setName("Noname");

        booking1 = new Booking();
        booking1.setFrom(LocalDate.of(2015, Month.OCTOBER, 22));
        booking1.setTo(LocalDate.of(2015, Month.OCTOBER, 30));
        booking1.setRoom(room);
        booking1.setTotal(new BigDecimal("1000.0"));
        booking1.setUser(admin);
        
        booking2 = new Booking();
        booking2.setFrom(LocalDate.of(2015, Month.NOVEMBER, 2));
        booking2.setTo(LocalDate.of(2015, Month.NOVEMBER, 6));
        booking2.setRoom(room);
        booking2.setTotal(new BigDecimal("1000.0"));
        booking2.setUser(user);
        
        booking3 = new Booking();
        booking3.setFrom(LocalDate.of(2015, Month.DECEMBER, 15));
        booking3.setTo(LocalDate.of(2015, Month.DECEMBER, 20));
        booking3.setRoom(room);
        booking3.setTotal(new BigDecimal("1000.0"));
        booking3.setUser(user);
        
        dr = new DateRange(LocalDate.of(2015, Month.OCTOBER, 21), LocalDate.of(2015, Month.DECEMBER, 30));
        
        bookings = new ArrayList<Booking>();
        bookings.add(booking1);
        bookings.add(booking2);
        bookings.add(booking3);
        
    }

    @BeforeMethod
    private void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getBookingsInRangeNullTest() {
    	adminService.getBookingsInRange(null, null);
    }
    
    @Test
    public void listReservedTest() {
        when(bookingDao.findAll()).thenReturn(bookings);
    	bookingService.book(booking1);
    	bookingService.book(booking2);
    	bookingService.book(booking3);
        verify(bookingDao).create(booking1);
        verify(bookingDao).create(booking2);
        verify(bookingDao).create(booking3);
        assertThat(adminService.listReserved(dr)).hasSize(2).containsExactly(user, admin);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void listReservedNullTest() {
    	adminService.listReserved(null);
    }
}
