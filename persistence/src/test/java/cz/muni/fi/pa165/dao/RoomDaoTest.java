package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.enums.RoomType;
import java.math.BigDecimal;
import java.util.Arrays;
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
 *
 * @author Miloslav Zezulka
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class RoomDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private HotelDao hotelDao;

    private Room r1;
    private Room r2;
    private Room r3;

    private Hotel h1;
    private Hotel h2;

    @BeforeMethod
    private void init() {
        r1 = new Room();
        r2 = new Room();
        r3 = new Room();

        h1 = new Hotel();
        h2 = new Hotel();

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
        r3.setHotel(h2);
        r3.setImage(new byte[0]);
        r3.setNumber(103);
        r3.setRecommendedPrice(new BigDecimal("3500.0"));
        r3.setType(RoomType.COMFORT_SINGLE_ROOM);

        h1.setAddress("In The Middle Of Nowhere");
        h1.setName("Noname");
        h1.setRooms(Arrays.asList(r1, r2));

        h2.setAddress("Pobrezni 1, Prague");
        h2.setName("Hilton");
        h2.setRooms(Arrays.asList(r3));

        hotelDao.create(h1);
        hotelDao.create(h2);
        roomDao.create(r1);
        roomDao.create(r2);
        roomDao.create(r3);
    }

    @Test
    public void findAll() {
        assertThat(roomDao.findAll()).hasSize(3).containsExactly(r1, r2, r3);
    }

    @Test
    public void findByHotelExisting() {
        assertThat(roomDao.findByHotel(h1)).containsExactly(r1, r2);
    }

    @Test
    public void findByHotelNonexistent() {
        Hotel h = new Hotel();
        assertThatThrownBy(() -> roomDao.findByHotel(h))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByNullHotel() {
        assertThatThrownBy(() -> roomDao.findByHotel(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByNumberExisting() {
        assertThat(roomDao.findByNumber(h1, 101)).isEqualTo(r1);
    }

    @Test
    public void findByNumberNonexistent() {
        assertThat(roomDao.findByNumber(h1, 1)).isNull();
    }

    @Test
    public void findByNumberNull() {
        assertThatThrownBy(() -> roomDao.findByNumber(null, Integer.SIZE))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> roomDao.findByNumber(h1, null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findById() {
        assertThat(roomDao.findById(r1.getId())).isEqualTo(r1);
    }

    @Test
    public void findByNullId() {
        assertThatThrownBy(() -> roomDao.findById(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByMissingId() {
        assertThat(Long.MAX_VALUE).isNotEqualTo(r1.getId());
        assertThat(Long.MAX_VALUE).isNotEqualTo(r2.getId());
        assertThat(Long.MAX_VALUE).isNotEqualTo(r3.getId());
        assertThat(roomDao.findById(Long.MAX_VALUE)).isNull();
    }

    @Test
    public void createNull() {
        assertThatThrownBy(() -> roomDao.create(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createExisting() {
        assertThatThrownBy(() -> roomDao.create(r1))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createHappyScenario() {
        Room r = new Room();
        r.setDescription("Single room.");
        r.setHotel(h2);
        r.setImage(new byte[0]);
        r.setNumber(101);
        r.setRecommendedPrice(new BigDecimal("3000.0"));
        r.setType(RoomType.SINGLE_ROOM);
        roomDao.create(r);
        assertThat(r.getId()).isNotNull();
        assertThat(roomDao.findById(r.getId())).isEqualTo(r);
        assertThat(roomDao.findAll()).hasSize(4);
    }

    @Test
    public void createTwice() {
        Room r = new Room();
        r.setDescription("Single room.");
        r.setHotel(h2);
        r.setImage(new byte[0]);
        r.setNumber(101);
        r.setRecommendedPrice(new BigDecimal("3000.0"));
        r.setType(RoomType.SINGLE_ROOM);
        roomDao.create(r);
        assertThatThrownBy(() -> roomDao.create(r))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateWithNullId() {
        Room r = new Room();
        r.setDescription("Single room.");
        r.setHotel(h2);
        r.setImage(new byte[0]);
        r.setNumber(101);
        r.setRecommendedPrice(new BigDecimal("3000.0"));
        r.setType(RoomType.SINGLE_ROOM);
        assertThatThrownBy(() -> roomDao.update(r))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateNull() {
        assertThatThrownBy(() -> roomDao.update(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateHappyScenario() {
        assertThat(roomDao.findAll()).hasSize(3);
        Room found = roomDao.findById(r1.getId());
        assertThat(found.getNumber()).isEqualTo(101);
        r1.setNumber(1);
        roomDao.update(r1);
        found = roomDao.findById(r1.getId());
        assertThat(found.getNumber()).isEqualTo(1);
        assertThat(roomDao.findAll()).hasSize(3).containsExactly(r1, r2, r3);
    }

    @Test
    public void removeWithNullId() {
        Room room = new Room();
        assertThatThrownBy(() -> roomDao.remove(room))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void removeNull() {
        assertThatThrownBy(() -> roomDao.remove(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void removeTwice() {
        roomDao.remove(r1);
        assertThatThrownBy(() -> roomDao.remove(r1)).isInstanceOf(DataAccessException.class);
    }
    
    @Test
    public void removeHappyScenario() {
        assertThat(roomDao.findAll()).hasSize(3);
        roomDao.remove(r1);
        assertThat(roomDao.findAll()).hasSize(2).containsExactly(r2, r3);
        roomDao.remove(r2);
        assertThat(roomDao.findAll()).hasSize(1).containsExactly(r3);
        roomDao.remove(r3);
        assertThat(roomDao.findAll()).isEmpty();
    }
}
