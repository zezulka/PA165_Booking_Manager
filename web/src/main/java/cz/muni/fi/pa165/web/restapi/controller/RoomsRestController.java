package cz.muni.fi.pa165.web.restapi.controller;



import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.dto.RoomCreateDTO;
import cz.muni.fi.pa165.api.dto.RoomDTO;
import cz.muni.fi.pa165.api.facade.RoomFacade;
import cz.muni.fi.pa165.web.restapi.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.web.restapi.exception.RestApiException;
import cz.muni.fi.pa165.web.restapi.exception.ServerProblemException;
import cz.muni.fi.pa165.web.restapi.hateoas.RoomResource;
import cz.muni.fi.pa165.web.restapi.hateoas.RoomResourceAssembler;


/**
 * 
 * @author Soňa Barteková
 *
 */
@RestController
@ExposesResourceFor(RoomDTO.class)
@RequestMapping("/rooms")
public class RoomsRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RoomsRestController.class);

    private RoomFacade roomFacade;
    private RoomResourceAssembler resourceAssembler;
    
    public RoomsRestController(@Autowired RoomFacade productFacade,
            @Autowired RoomResourceAssembler productResourceAssembler) {
        this.roomFacade = productFacade;
        this.resourceAssembler = productResourceAssembler;
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createRoom(@RequestBody RoomCreateDTO room) throws ResourceNotFoundException {

        LOGGER.debug("rest createRoom()");

        try {
            roomFacade.createRoom(room);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("room already exists");
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteRoom(@PathVariable("id") long id) throws RestApiException {
        LOGGER.debug("[REST] delete({})", id);
        try {
        	RoomDTO room = roomFacade.findById(id);
            roomFacade.deleteRoom(room);
        } catch (IllegalArgumentException _e) {
            String msg = String.format("room with id %d not found", id);
            LOGGER.error(msg);
            throw new ResourceNotFoundException(msg);
        } catch (Throwable ex) {
            StringBuilder bldr = new StringBuilder(String.format("cannot delete room with id %d\n", id));
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
    public final HttpEntity<Resources<RoomResource>> getRooms() {
        LOGGER.debug("[REST] getRooms()");
        List<RoomResource> resourceCollection = resourceAssembler.toResources(roomFacade.findAll());
        Resources<RoomResource> rooms = new Resources<>(resourceCollection,
                linkTo(RoomsRestController.class).withSelfRel(),
                linkTo(RoomsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }	
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<RoomResource> getRoom(@PathVariable("id") long id) 
            throws ResourceNotFoundException {
        LOGGER.debug("[REST] getRoom({})", id);
        RoomDTO roomDTO = roomFacade.findById(id);
        if (roomDTO == null) {
            throw new ResourceNotFoundException("room " + id + " not found");
        }
        RoomResource resource = resourceAssembler.toResource(roomDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
	}
    
    @RequestMapping(value = "?number={number}", method = RequestMethod.GET)
	public final HttpEntity<RoomResource> getRoomByNumber(@RequestBody HotelDTO hotel, @PathVariable("number") Integer number) 
            throws ResourceNotFoundException {
        LOGGER.debug("[REST] getRoomByNumber({})", number);
        RoomDTO roomDTO = roomFacade.findByNumber(hotel,number);
        if (roomDTO == null) {
            throw new ResourceNotFoundException("room with number " + number + " not found");
        }
        RoomResource resource = resourceAssembler.toResource(roomDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
	}


}
