package cz.muni.fi.pa165.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.testng.annotations.BeforeMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.entity.Room;
import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.dao.HotelDao;
import cz.muni.fi.pa165.dao.RoomDao;

@ContextConfiguration(classes=ServiceConfiguration.class)
public final class RoomServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private RoomDao roomDao;

    @Mock
    private HotelDao hotelDao;

    @Autowired
    @InjectMocks
    private RoomService roomService;

    @BeforeClass
    private void SetUp() throws Exception {
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

//    @Test
//    public void findAllEmpty() {
//        List<Room> tmp = new ArrayList<>();
//        Assert.assertTrue(tmp.isEmpty());
//
//        when(roomDao.findAll()).thenReturn(tmp);
//        List<Room> tmp = roomService.findAll();
//        verify(roomDao).findAll();
//        Assert.assertEquals(tmp, l1);
//    }

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
}