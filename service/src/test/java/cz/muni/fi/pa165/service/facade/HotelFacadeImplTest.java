package cz.muni.fi.pa165.service.facade;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import cz.muni.fi.pa165.api.dto.HotelCreateDTO;
import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.facade.HotelFacade;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Petr Valenta
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class HotelFacadeImplTest {

    @Autowired
    private HotelFacade hotelFacade = new HotelFacadeImpl();

    private HotelCreateDTO hotelCreateDTO;

    @Before
    public void init(){
        hotelCreateDTO = new HotelCreateDTO();
        hotelCreateDTO.setName("Generic Name");
        hotelCreateDTO.setAddress("Krno");
    }

    @Test
    public void testCreateHotel() throws Exception {
        hotelFacade.createHotel(hotelCreateDTO);
    }

    @Test
    public void testFindAll() throws Exception {
        hotelFacade.createHotel(hotelCreateDTO);
        List<HotelDTO> tmp = hotelFacade.findAll();
        assertEquals(tmp.size(),1);
    }

    @Test
    public void testFindById() throws Exception {
        hotelFacade.createHotel(hotelCreateDTO);
        List<HotelDTO> tmp = hotelFacade.findAll();
        assertEquals(tmp.size(),1);

        long id = tmp.get(0).getId();
        HotelDTO hot = hotelFacade.findById(id);
        assertNotNull(hot);
    }

    @Test
    public void testFindByName() throws Exception {
        hotelFacade.createHotel(hotelCreateDTO);
        HotelDTO tmp = hotelFacade.findByName("Generic Name");
    }
}