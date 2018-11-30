package cz.muni.fi.pa165.service.facade;

import java.util.Arrays;
import java.util.List;

import cz.muni.fi.pa165.api.dto.HotelCreateDTO;
import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.facade.HotelFacade;
import cz.muni.fi.pa165.entity.Hotel;
import cz.muni.fi.pa165.service.HotelService;
import cz.muni.fi.pa165.service.auxiliary.BeanMappingService;
import cz.muni.fi.pa165.service.auxiliary.BeanMappingServiceImpl;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;

import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.argThat;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.inject.Inject;

/**
 * @author Petr Valenta
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HotelFacadeImplTest {

    @Inject
    @Spy
    private BeanMappingService beanMappingService;
    @Mock
    private HotelService hotelService;

    @InjectMocks
    private HotelFacade hotelFacade = new HotelFacadeImpl();

    private HotelDTO hDTO1;
    private HotelCreateDTO hCreateDTO1;
    private Hotel hotel1;

    private HotelDTO hDTO2;
    private Hotel hotel2;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        hDTO1 = new HotelDTO();
        hCreateDTO1 = new HotelCreateDTO();
        hotel1 = new Hotel();

        hDTO1.setName("Generic Name");
        hDTO1.setAddress("Krno");

        hCreateDTO1.setName("Generic Name");
        hCreateDTO1.setAddress("Krno");

        hotel1.setName("Generic Name");
        hotel1.setAddress("Krno");

        hDTO2 = new HotelDTO();
        hotel2 = new Hotel();

        hDTO2.setName("Unique Name");
        hDTO2.setAddress("Brnein");

        hotel2.setName("Unique Name");
        hotel2.setAddress("Brnein");
    }

    @Test
    public void testGetAll() {
        List<Hotel> listBoth = Arrays.asList(hotel1, hotel2);
        when(hotelService.findAll()).thenReturn(listBoth);

        List<HotelDTO> result = hotelFacade.findAll();

        verify(hotelService).findAll();
        assertThat(2).isEqualTo(result.size());
        assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        when(hotelService.findById(1L)).thenReturn(hotel1);
        HotelDTO result = hotelFacade.findById(1L);
        verify(hotelService).findById(1L);

        assertEquals(result, hDTO1);
    }


    @Test
    public void testFindByName() {
        when(hotelService.findByName("Unique Name")).thenReturn(hotel2);
        HotelDTO result = hotelFacade.findByName("Unique Name");
        verify(hotelService).findByName("Unique Name");

        assertEquals(result, hDTO2);
    }

    @Test
    public void testCreateHotel() {
        hotelFacade.createHotel(hCreateDTO1);
        verify(hotelService).create(argThat(hCreateDTO1ArgMatcher()));

    }

    private ArgumentMatcher<Hotel> hCreateDTO1ArgMatcher() {
        return new ArgumentMatcher<Hotel>() {
            @Override
            public boolean matches(Object argument) {
                Hotel h = (Hotel) argument;
                return hCreateDTO1.getName().equals(h.getName());
            }
        };
    }
}