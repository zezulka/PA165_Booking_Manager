package cz.muni.fi.pa165.web.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.api.facade.UserFacade;
import cz.muni.fi.pa165.web.restapi.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.web.restapi.hateoas.UserResource;
import cz.muni.fi.pa165.web.restapi.hateoas.UserResourceAssembler;

/**
 * @author Petr Valenta
 */
@RestController
@ExposesResourceFor(UserDTO.class)
@RequestMapping("/users")
public class UsersRestController {

    private static Logger LOGGER = LoggerFactory.getLogger(HotelsRestController.class);

    private UserFacade facade;
    private UserResourceAssembler resourceAssembler;

    public UsersRestController(@Autowired UserFacade facade, @Autowired UserResourceAssembler resourceAssembler) {
        this.facade = facade;
        this.resourceAssembler = resourceAssembler;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<UserResource> getUserById(@PathVariable("id") long id)
        throws ResourceNotFoundException {
        LOGGER.debug("[REST] getUser({})", id);
        UserDTO userDTO = facade.findById(id);
        if (userDTO == null) {
            throw new ResourceNotFoundException("user " + id + " not found");
        }
        UserResource resource = resourceAssembler.toResource(userDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}