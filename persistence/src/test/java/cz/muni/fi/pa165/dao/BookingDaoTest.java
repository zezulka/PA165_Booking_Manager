package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Booking;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.enums.RoomType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;

import static org.assertj.core.api.Assertions.*;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author Petr Valenta
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BookingDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HotelDao hotelDao;

    private Room r1;
    private Room r2;
    private Room r3;

    private Booking b1;
    private Booking b2;
    private Booking b3;

    private User c1;
    private User c2;

    private Hotel h1;

    /**
     * Setup modified from other tests
     */
    @BeforeMethod
    private void init() {
        r1 = new Room();
        r2 = new Room();
        r3 = new Room();

        b1 = new Booking();
        b2 = new Booking();
        b3 = new Booking();

        c1 = new User();
        c2 = new User();

        h1 = new Hotel();

        r1.setDescription("Single room, beautiful view.");
        r1.setHotel(h1);
        r1.setImage(new byte[0]);
        r1.setNumber(101);
        r1.setRecommendedPrice(new BigDecimal("2000.0"));
        r1.setType(RoomType.SINGLE_ROOM);

        r2.setDescription("Double room, comfy beds!");
        r2.setHotel(h1);
        r2.setImage(new byte[0]);
        r2.setNumber(102);
        r2.setRecommendedPrice(new BigDecimal("2300.0"));
        r2.setType(RoomType.DOUBLE_ROOM);

        r3.setDescription("Single room, comfy beds and free champagne!");
        r3.setHotel(h1);
        r3.setImage(new byte[0]);
        r3.setNumber(103);
        r3.setRecommendedPrice(new BigDecimal("3500.0"));
        r3.setType(RoomType.COMFORT_SINGLE_ROOM);

        c1.setEmail("teriductyl@jurassic.com");
        c1.setFirstName("Teri");
        c1.setSurname("Ductyl");
        c1.setAdministrator(false);
        c1.setPasswordHash("IAmDead123");

        c2.setEmail("paige.turner@book.com");
        c2.setFirstName("Paige");
        c2.setSurname("Turner");
        c2.setAdministrator(true);
        c2.setPasswordHash("TurnToPage394");

        b1.setTotal(new BigDecimal("1.5"));
        b1.setFromDate(LocalDate.of(2030, 6, 23));
        b1.setToDate(LocalDate.of(2030, 6, 25));
        b1.setUser(c1);
        b1.setRoom(r1);

        b2.setTotal(new BigDecimal("2.5"));
        b2.setFromDate(LocalDate.of(2032,6,23));
        b2.setToDate(LocalDate.of(2032,6,25));
        b2.setUser(c2);
        b2.setRoom(r2);

        b3.setId(Long.MAX_VALUE-1);

        h1.setAddress("In The Middle Of Nowhere");
        h1.setName("Noname");
        List<Room> list = new ArrayList<>();
        list.add(r1);
        list.add(r2);
        h1.setRooms(list);

        hotelDao.create(h1);

        roomDao.create(r1);
        roomDao.create(r2);
        roomDao.create(r3);

        userDao.create(c1);
        userDao.create(c2);

        bookingDao.create(b1);
        bookingDao.create(b2);
    }

    @Test
    public void findAll() {
        assertThat(bookingDao.findAll()).hasSize(2).containsExactly(b1,b2);
    }

    @Test
    public void findById() {
        assertThat(bookingDao.findById(b1.getId())).isEqualTo(b1);
    }

    @Test
    public void findByNullId() {
        assertThatThrownBy(() -> bookingDao.findById(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByMissingId() {
        assertThat(Long.MAX_VALUE).isNotEqualTo(b1.getId());
        assertThat(Long.MAX_VALUE).isNotEqualTo(b2.getId());
        assertThat(bookingDao.findById(Long.MAX_VALUE)).isNull();
    }

    @Test
    public void findByRoom() {
        assertThat(bookingDao.findByRoom(r1)).hasSize(1).containsExactly(b1);
    }

    @Test
    public void findByNullRoom() {
        assertThatThrownBy(() -> bookingDao.findByRoom(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByNonUsedRoom() {
        assertThat(bookingDao.findByRoom(r3)).isEmpty();
    }

    @Test
    public void createNull() {
        assertThatThrownBy(() -> bookingDao.create(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createNoNullId() {
        assertThatThrownBy(() -> bookingDao.create(b3))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createExisting() {
        assertThatThrownBy(() -> bookingDao.create(b1))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateNull() {
        assertThatThrownBy(() -> bookingDao.update(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateNullId() {
        Booking bnull = new Booking();
        bnull.setId(null);

        assertThatThrownBy(() -> bookingDao.update(bnull))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void removeNull() {
        assertThatThrownBy(() -> bookingDao.remove(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateHappyScenario() {
        assertThat(bookingDao.findAll()).hasSize(2);
        Booking found = bookingDao.findById(b1.getId());
        assertThat(found.getUser()).isEqualTo(c1);
        b1.setUser(c2);
        bookingDao.update(b1);
        found = bookingDao.findById(b1.getId());
        assertThat(found.getUser()).isEqualTo(c2);
        assertThat(bookingDao.findAll()).hasSize(2).containsExactly(b1,b2);
    }

    @Test
    public void removeHappyScenario() {
        assertThat(bookingDao.findAll()).hasSize(2);
        bookingDao.remove(b1);
        assertThat(bookingDao.findAll()).hasSize(1).containsExactly(b2);
    }

    @Test
    public void createHappyScenario() {
        assertThat(bookingDao.findAll()).hasSize(2);
        Booking b4 = new Booking();
        b4.setTotal(new BigDecimal("2800.98"));
        b4.setFromDate(LocalDate.of(2033,6,23));
        b4.setToDate(LocalDate.of(2033,6,20));
        b4.setUser(c2);
        b4.setRoom(r2);
        bookingDao.create(b4);
        assertThat(bookingDao.findAll()).hasSize(3).containsExactly(b1, b2, b4);
    }
}
