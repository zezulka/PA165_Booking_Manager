package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.dto.RoomCreateDTO;
import cz.muni.fi.pa165.api.dto.RoomDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.api.facade.RoomFacade;
import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.RoomService;
import cz.muni.fi.pa165.service.auxiliary.BeanMappingService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.assertj.core.util.Arrays;
import org.dozer.inject.Inject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
@Ignore
public class RoomFacadeTest {

    @Autowired
    private RoomFacade roomFacade = new RoomFacadeImpl();

    private RoomCreateDTO roomCreate;

    private RoomDTO room;

    private HotelDTO hotel;

    public RoomFacadeTest() {
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void init() {
        roomCreate = new RoomCreateDTO();
        room = new RoomDTO();
        hotel = new HotelDTO();
        
        room.setDescription("Cozy room. Very cheap.");
        room.setNumber(101);
        room.setId(1L);
        room.setType(RoomType.SINGLE_ROOM);
        room.setRecommendedPrice(BigDecimal.ONE);
        room.setImage(new byte[0]); 
        
        hotel.setAddress("Foobarville, 65");
        hotel.setName("In the middle of nowhere");
        List<RoomDTO> rooms = new ArrayList<>();
        rooms.add(room);
        hotel.setRooms(rooms);
        hotel.setId(1L);    
        
        room.setHotel(hotel);
        
        roomCreate.setHotel(hotel);
        roomCreate.setNumber(101);
    }

    @Test
    public void findAll() {
        roomFacade.findAll();
    }

    @Test
    public void findById() {
        roomFacade.findById(1L);
    }


}
