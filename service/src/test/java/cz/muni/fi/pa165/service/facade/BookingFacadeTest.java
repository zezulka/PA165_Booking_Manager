package cz.muni.fi.pa165.service.facade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.muni.fi.pa165.api.dto.BookingDTO;
import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.dto.RoomDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.api.facade.BookingFacade;
import cz.muni.fi.pa165.enums.RoomType;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Soňa Barteková
 *
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookingFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private BookingFacade bookingFacade = new BookingFacadeImpl();

    private RoomDTO rDTO;
    private HotelDTO hDTO;
    private BookingDTO bDTO;
    private UserDTO uDTO;

    @BeforeMethod
    public void init() {

        rDTO = new RoomDTO();
        hDTO = new HotelDTO();
        bDTO = new BookingDTO();
        uDTO = new UserDTO();

        rDTO.setDescription("Single room, beautiful view.");
        rDTO.setHotel(hDTO);
        rDTO.setImage(new byte[0]);
        rDTO.setNumber(101);
        rDTO.setRecommendedPrice(new BigDecimal("2000.0"));
        rDTO.setType(RoomType.SINGLE_ROOM);

        hDTO.setAddress("In The Middle Of Nowhere");
        hDTO.setName("Noname");

        bDTO.setId(1l);
        bDTO.setFromDate(LocalDate.of(2015, Month.OCTOBER, 22));
        bDTO.setToDate(LocalDate.of(2015, Month.OCTOBER, 30));
        bDTO.setRoom(rDTO);
        bDTO.setTotal(new BigDecimal("1000.0"));
        bDTO.setUser(uDTO);

    }

    @Test
    public void getAllBookingsTest() {
        bookingFacade.getAllBookings();
    }

}
