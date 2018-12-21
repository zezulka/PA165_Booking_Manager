package cz.muni.fi.pa165.web.restapi.controller;

import cz.muni.fi.pa165.api.DateRange;
import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.facade.HotelFacade;
import cz.muni.fi.pa165.api.facade.RoomFacade;
import cz.muni.fi.pa165.web.restapi.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.web.restapi.exception.RestApiException;
import cz.muni.fi.pa165.web.restapi.exception.ServerProblemException;
import cz.muni.fi.pa165.web.restapi.hateoas.HotelResource;
import cz.muni.fi.pa165.web.restapi.hateoas.HotelResourceAssembler;
import cz.muni.fi.pa165.web.restapi.hateoas.RoomResource;
import cz.muni.fi.pa165.web.restapi.hateoas.RoomResourceAssembler;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Miloslav Zezulka
 */
@RestController
@ExposesResourceFor(HotelDTO.class)
@RequestMapping("/hotels")
public class HotelsRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(HotelsRestController.class);

    private HotelFacade hotelFacade;
    private RoomFacade roomFacade;
    private HotelResourceAssembler hotelResourceAssembler;
    private RoomResourceAssembler roomResourceAssembler;
    private EntityLinks entityLinks;

    public HotelsRestController(@Autowired HotelFacade hotelFacade,
            @Autowired RoomFacade roomFacade,
            @Autowired HotelResourceAssembler productResourceAssembler,
            @Autowired RoomResourceAssembler roomResourceAssembler,
            @SuppressWarnings("SpringJavaAutowiringInspection")
            @Autowired EntityLinks entityLinks) {
        this.hotelFacade = hotelFacade;
        this.roomFacade = roomFacade;
        this.hotelResourceAssembler = productResourceAssembler;
        this.roomResourceAssembler = roomResourceAssembler;
        this.entityLinks = entityLinks;
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<HotelResource>> getProducts() {
        LOGGER.debug("[REST] getHotels()");
        List<HotelResource> resourceCollection = hotelResourceAssembler.toResources(hotelFacade.findAll());
        Resources<HotelResource> hotels = new Resources<>(resourceCollection,
                linkTo(HotelsRestController.class).withSelfRel(),
                linkTo(HotelsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void updateHotel(@PathVariable("id") long id, @RequestBody @Valid HotelDTO hotelDTO,
            BindingResult bindingResult) {
        LOGGER.debug("[REST] updateHotel()");
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Invalid hotel.");
        }
        hotelFacade.update(hotelDTO);
    }

    @RequestMapping(value = "/{id}/rooms", method = RequestMethod.GET)
    public final HttpEntity<Resources<RoomResource>> getRoomsByHotel(@PathVariable("id") long id) throws ResourceNotFoundException {
        LOGGER.debug("[REST] getRoomsByHotel({})", id);
        HotelDTO hotelDTO = hotelFacade.findById(id);
        if (hotelDTO == null) {
            throw new ResourceNotFoundException("hotel " + id + " not found");
        }
        List<RoomResource> resource = roomResourceAssembler.toResources(roomFacade.findByHotel(hotelDTO));
        Link toSelf = entityLinks.linkForSingleResource(HotelDTO.class, id).slash("/rooms").withSelfRel();
        return new ResponseEntity<>(new Resources<>(resource, toSelf), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/vacancy", method = RequestMethod.GET)
    public final HttpEntity<Resources<RoomResource>> getFreeRoomsByHotel(
            @PathVariable("id") long id,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to) throws ResourceNotFoundException {
        LOGGER.debug("[REST] getFreeRoomsByHotelAndRange({})", id);
        if (from == null) {
            from = LocalDate.MIN.toString();
        }
        if (to == null) {
            to = LocalDate.MAX.toString();
        }
        DateRange range = getRange(from, to);

        HotelDTO hotelDTO = hotelFacade.findById(id);
        if (hotelDTO == null) {
            throw new ResourceNotFoundException("hotel " + id + " not found");
        }

        List<RoomResource> resourceCollection = roomResourceAssembler.toResources(roomFacade.getAvailableRooms(range, hotelDTO));

        Resources<RoomResource> rooms = new Resources<>(resourceCollection,
                linkTo(RoomsRestController.class).withSelfRel(),
                linkTo(RoomsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<HotelResource> getProduct(@PathVariable("id") long id)
            throws ResourceNotFoundException {
        LOGGER.debug("[REST] getProduct({})", id);
        HotelDTO hotelDTO = hotelFacade.findById(id);
        if (hotelDTO == null) {
            throw new ResourceNotFoundException("hotel " + id + " not found");
        }
        HotelResource resource = hotelResourceAssembler.toResource(hotelDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) throws RestApiException {
        LOGGER.debug("[REST] delete({})", id);
        try {
            hotelFacade.delete(id);
        } catch (IllegalArgumentException _e) {
            String msg = String.format("hotel with id %d not found", id);
            LOGGER.error(msg);
            throw new ResourceNotFoundException(msg);
        } catch (Throwable ex) {
            StringBuilder bldr = new StringBuilder(String.format("cannot delete hotel with id %d\n", id));
            Throwable rootCause = ex;
            while ((ex = ex.getCause()) != null) {
                rootCause = ex;
                bldr.append(String.format("caused by %s, reason: %s\n", ex.getClass().getSimpleName(), ex.getMessage()));
            }
            LOGGER.error(bldr.toString());
            throw new ServerProblemException(rootCause.getMessage());
        }
    }

    private DateRange getRange(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        return new DateRange(fromDate, toDate);
    }
}
