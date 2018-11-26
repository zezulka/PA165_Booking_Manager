package cz.muni.fi.pa165.service.facade;

import static org.testng.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import cz.muni.fi.pa165.api.dto.HotelCreateDTO;
import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.facade.HotelFacade;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;

/**
 * @author Petr Valenta
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class HotelFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private HotelFacade hotelFacade = new HotelFacadeImpl();

    private HotelCreateDTO hotelCreateDTO;

    @BeforeMethod
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