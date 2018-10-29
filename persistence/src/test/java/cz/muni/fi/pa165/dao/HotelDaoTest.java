package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.enums.RoomType;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import javax.persistence.NoResultException;

/**
 *
 * @author Martin Páleník
 */
@ContextConfiguration(classes= PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HotelDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private HotelDao hotelDao;

    private Room r1;
    private Room r2;
    private Room r3;

    private Hotel h1;
    private Hotel h2;

    /**
     * Setup kindly forged from Mr. Zezulka
     */
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
        assertThat(hotelDao.findAll()).hasSize(2).containsExactly(h1, h2);
    }

    @Test
    public void findByNameExisting() {
        assertThat(hotelDao.findByName(h1.getName())).isEqualTo(h1);
    }

    @Test
    public void findByNameNonexistent() {
        assertThat(hotelDao.findByName("There is just no way any hotel would ever be called like this."))
                .isNull();
    }

    @Test
    public void findByNullName() {
        assertThatThrownBy(() -> hotelDao.findByName(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findById() {
        assertThat(hotelDao.findById(h1.getId())).isEqualTo(h1);
    }

    @Test
    public void findByNullId() {
        assertThatThrownBy(() -> hotelDao.findById(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByMissingId() {
        assertThat(Long.MAX_VALUE).isNotEqualTo(h1.getId());
        assertThat(Long.MAX_VALUE).isNotEqualTo(h2.getId());
        assertThat(hotelDao.findById(Long.MAX_VALUE)).isNull();
    }

    @Test
    public void createNull() {
        assertThatThrownBy(() -> hotelDao.create(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createExisting() {
        assertThatThrownBy(() -> hotelDao.create(h1))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createTwice() {
        Hotel h = new Hotel();
        h.setName("Grand");
        h.setAddress("Benesova 605/18, Brno 60200");
        h.setRooms(new ArrayList<Room>());

        hotelDao.create(h);
        assertThatThrownBy(() -> hotelDao.create(h))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateNull() {
        assertThatThrownBy(() -> hotelDao.update(null))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateWithNullId() {
        Hotel h = new Hotel();
        h.setName("Grand");
        h.setAddress("Benesova 605/18, Brno 60200");
        h.setRooms(new ArrayList<Room>());

        assertThatThrownBy(() -> hotelDao.update(h))
                .isInstanceOf(DataAccessException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void removeNull() {
        assertThatThrownBy(() -> hotelDao.remove(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void removeWithNullId() {
        Hotel hotel = new Hotel();
        assertThatThrownBy(() -> hotelDao.remove(hotel))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createHappyScenario() {
        Hotel h = new Hotel();
        h.setName("Grand");
        h.setAddress("Benesova 605/18, Brno 60200");
        h.setRooms(new ArrayList<Room>());

        hotelDao.create(h);
        assertThat(h.getId()).isNotNull();
        assertThat(hotelDao.findById(h.getId())).isEqualTo(h);
        assertThat(hotelDao.findAll()).hasSize(3);
    }

    @Test
    public void removeHappyScenario() {
        assertThat(hotelDao.findAll()).hasSize(2);
        roomDao.remove(r1);
        roomDao.remove(r2);
        hotelDao.remove(h1);
        assertThat(hotelDao.findAll()).hasSize(1).containsExactly(h2);
        roomDao.remove(r3);
        hotelDao.remove(h2);
        assertThat(hotelDao.findAll()).isEmpty();
    }
}