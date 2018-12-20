package cz.muni.fi.pa165.web.restapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.api.dto.BookingCreateDTO;
import cz.muni.fi.pa165.api.dto.BookingDTO;
import cz.muni.fi.pa165.api.facade.BookingFacade;
import cz.muni.fi.pa165.web.restapi.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.web.restapi.exception.RestApiException;
import cz.muni.fi.pa165.web.restapi.exception.ServerProblemException;
import cz.muni.fi.pa165.web.restapi.hateoas.BookingResource;
import cz.muni.fi.pa165.web.restapi.hateoas.BookingResourceAssembler;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Soňa Barteková
 *
 */
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
        LOGGER.debug("rest createBooking({})", booking);
        bookingFacade.createBooking(booking);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) throws RestApiException {
        LOGGER.debug("[REST] delete({})", id);
        try {
            bookingFacade.cancelBooking(id);
        } catch (IllegalArgumentException _e) {
            String msg = String.format("booking with id %d not found", id);
            LOGGER.error(msg);
            throw new ResourceNotFoundException(msg);
        } catch (Throwable ex) {
            StringBuilder bldr = new StringBuilder(String.format("cannot delete booking with id %d\n", id));
            Throwable rootCause = ex;
            while ((ex = ex.getCause()) != null) {
                rootCause = ex;
                bldr.append(String.format("caused by %s, reason: %s\n", ex.getClass().getSimpleName(), ex.getMessage()));
            }
            LOGGER.error(bldr.toString());
            throw new ServerProblemException(rootCause.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<BookingResource>> getBookings(
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "room", required = false) Long room) {

        List<BookingResource> resourceCollection;
        if (room != null) {
            LOGGER.debug("[REST] getBookingsByRange({}, {}, {})", from, to, room);
            if (from == null) {
                from = LocalDate.MIN.toString();
            }
            if (to == null) {
                to = LocalDate.MAX.toString();
            }
            DateRange range = getRange(from, to);
            resourceCollection = resourceAssembler.toResources(bookingFacade.findBookingsByRange(range, room));
        } else {
            LOGGER.debug("[REST] getBookings()");
            resourceCollection = resourceAssembler.toResources(bookingFacade.getAllBookings());
        }
        Resources<BookingResource> rooms = new Resources<>(resourceCollection,
                linkTo(BookingsRestController.class).withSelfRel(),
                linkTo(BookingsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/byUser", method = RequestMethod.GET)
    public final HttpEntity<Resources<BookingResource>> getUsersBookings(
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "user", required = false) Long user) {

        LOGGER.debug("[REST] getUsersBookings({}, {}, {})", from, to, user);
        if (from == null) {
            from = LocalDate.MIN.toString();
        }
        if (to == null) {
            to = LocalDate.MAX.toString();
        }
        DateRange range = getRange(from, to);
        List<BookingResource> resourceCollection = resourceAssembler.toResources(bookingFacade.findBookingsByRangeByUser(range, user));
        Resources<BookingResource> bookings = new Resources<>(resourceCollection,
                linkTo(UsersRestController.class).withSelfRel(),
                linkTo(UsersRestController.class).slash("/authenticate").withRel("authenticate"));
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<BookingResource> getBooking(@PathVariable("id") long id)
            throws ResourceNotFoundException {
        LOGGER.debug("[REST] getBooking({})", id);
        BookingDTO booking = bookingFacade.getBooking(id);
        if (booking == null) {
            throw new ResourceNotFoundException("booking " + id + " not found");
        }
        BookingResource resource = resourceAssembler.toResource(booking);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/discount", method = RequestMethod.GET)
    public BigDecimal getDiscount(@PathVariable("id") long id)
            throws ResourceNotFoundException {
        LOGGER.debug("[REST] getDiscount({})", id);
        BookingDTO booking = bookingFacade.getBooking(id);
        if (booking == null) {
            throw new ResourceNotFoundException("booking " + id + " not found");
        }
        BigDecimal discount = bookingFacade.calculateDiscount(booking);
        return discount;
    }

    private DateRange getRange(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        return new DateRange(fromDate, toDate);
    }
}
