package cz.muni.fi.pa165.web.restapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.api.dto.BookingCreateDTO;
import cz.muni.fi.pa165.api.dto.BookingDTO;
import cz.muni.fi.pa165.api.facade.BookingFacade;
import cz.muni.fi.pa165.web.restapi.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.web.restapi.hateoas.BookingResourceAssembler;

@RestController
@ExposesResourceFor(BookingDTO.class)
@RequestMapping("/bookings")
public class BookingsRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookingsRestController.class);

    private BookingFacade bookingFacade;
    private BookingResourceAssembler resourceAssembler;

    public BookingsRestController(@Autowired BookingFacade productFacade,
            @Autowired BookingResourceAssembler productResourceAssembler) {
        this.bookingFacade = productFacade;
        this.resourceAssembler = productResourceAssembler;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createBooking(@RequestBody BookingCreateDTO booking) throws ResourceNotFoundException {

        LOGGER.debug("rest createBooking()");

        try {
            bookingFacade.createBooking(booking);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("booking already exists");
        }
    }


}
