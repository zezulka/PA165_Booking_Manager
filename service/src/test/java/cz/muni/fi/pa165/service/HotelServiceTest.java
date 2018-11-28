package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.HotelDao;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
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
 * @author Soňa Barteková
 *
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public final class HotelServiceTest {

    @Mock
    private HotelDao hotelDao;

    @Autowired
    @InjectMocks
    private HotelService hotelService;

    @Before
    public void SetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    private Room r1;
    private Room r2;
    private Room r3;

    private Hotel h1;
    private Hotel h2;

    private List<Hotel> hotels;

    @Before
    public void init() {
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

        hotels = new ArrayList<Hotel>();
        hotels.add(h1);
        hotels.add(h2);

    }

    @Test
    public void createHotelCorrectTest() {
        hotelService.create(h1);
        verify(hotelDao).create(h1);
    }

    @Test
    public void createHotelNullTest() {
        assertThatThrownBy(() -> hotelService.create(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteHotelCorrectTest() {
        hotelService.delete(h1);
        verify(hotelDao).remove(h1);
    }

    @Test
    public void deleteHotelNullTest() {
        assertThatThrownBy(() -> hotelService.delete(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateHotelCorrectTest() {
        hotelService.update(h1);
        verify(hotelDao).update(h1);
    }

    @Test
    public void updateHotelNullTest() {
        assertThatThrownBy(() -> hotelService.update(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findAllCorrectTest() {
        when(hotelDao.findAll()).thenReturn(hotels);
        List<Hotel> h = hotelService.findAll();
        verify(hotelDao).findAll();
        assertEquals(h, hotels);
    }

    @Test
    public void findAllNullTest() {
        List<Hotel> h = new ArrayList<>();
        when(hotelDao.findAll()).thenReturn(h);
        h = hotelService.findAll();
        verify(hotelDao).findAll();
        assertEquals(h, Collections.EMPTY_LIST);
    }

    @Test
    public void findByIdCorrectTest() {
        when(hotelDao.findById(1L)).thenReturn(h1);
        Hotel h = hotelService.findById(1L);
        verify(hotelDao).findById(1L);
        assertEquals(h, h1);
    }

    @Test
    public void findByIdNullTest() {
        assertThatThrownBy(() -> hotelService.findById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByNameCorrectTest() {
        when(hotelDao.findByName("Noname")).thenReturn(h1);
        Hotel h = hotelService.findByName("Noname");
        verify(hotelDao).findByName("Noname");
        assertEquals(h, h1);
    }

    @Test
    public void findByNameNullTest() {
        assertThatThrownBy(() -> hotelService.findByName(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
