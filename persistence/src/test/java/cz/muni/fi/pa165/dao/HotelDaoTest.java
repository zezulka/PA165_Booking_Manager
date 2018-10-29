package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.enums.RoomType;
import java.math.BigDecimal;
import java.util.Arrays;
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
     * Setup kindly forged from Mr. Zezulka's
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
    public void findByHotelExisting() {
        assertThat(hotelDao.findByName(h1.getName())).isEqualTo(h1);
    }
}