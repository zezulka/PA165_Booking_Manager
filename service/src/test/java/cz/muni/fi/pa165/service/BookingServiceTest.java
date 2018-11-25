package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.BookingDao;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.auxiliary.DateService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
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

    private Room r1;
    private Hotel h1;
    private Booking b;
    private List<Room> l1;

    @BeforeMethod
    public void simulateSystemDate(){
    	LocalDate backToTheFuture = LocalDate.of(2015, Month.OCTOBER, 21);
    	when(dateService.getCurrentDate()).thenReturn(backToTheFuture);
}
    
    @BeforeMethod
    public void init() {
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
        
        b = new Booking();
        b.setFrom(LocalDate.MIN);
    }

    @BeforeClass
    private void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void bookNull() {
        assertThatThrownBy(() -> bookingService.book(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void cancelNull() {
        assertThatThrownBy(() -> bookingService.cancel(null))
                .isInstanceOf(IllegalArgumentException.class);
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
}
