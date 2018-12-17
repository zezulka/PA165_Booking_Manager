package cz.muni.fi.pa165.web.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.pa165.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.api.facade.UserFacade;
import cz.muni.fi.pa165.web.restapi.exception.IllegalRequestException;
import cz.muni.fi.pa165.web.restapi.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.web.restapi.exception.ServerProblemException;
import cz.muni.fi.pa165.web.restapi.hateoas.UserResource;
import cz.muni.fi.pa165.web.restapi.hateoas.UserResourceAssembler;
import java.util.Collections;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<UserResource>> getUsers() {
        LOGGER.debug("[REST] getUsers()");
        List<UserResource> resourceCollection = resourceAssembler.toResources(facade.getAll());
        Resources<UserResource> users = new Resources<>(resourceCollection,
                linkTo(UsersRestController.class).withSelfRel(),
                linkTo(UsersRestController.class).slash("/authenticate").withRel("authenticate"));
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<UserResource> getUserById(@PathVariable("id") long id)
            throws ResourceNotFoundException {
        LOGGER.debug("[REST] getUserById({})", id);
        UserDTO userDTO = facade.findById(id);
        if (userDTO == null) {
            throw new ResourceNotFoundException("user " + id + " not found");
        }
        UserResource resource = resourceAssembler.toResource(userDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/email/{email}/", method = RequestMethod.GET)
    public final HttpEntity<UserResource> getUserByEmail(@PathVariable("email") String email)
            throws ResourceNotFoundException {
        LOGGER.debug("[REST] getUserByEmail({})", email);
        UserDTO userDTO = facade.findByEmail(email);
        if (userDTO == null) {
            throw new ResourceNotFoundException("user " + email + " not found");
        }
        UserResource resource = resourceAssembler.toResource(userDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public boolean authenticateUser(@RequestBody UserAuthenticateDTO userAuthenticateDTO)
            throws ServerProblemException {
        LOGGER.debug("[REST] authenticateUser({})");

        try {
            boolean ret = facade.authenticate(userAuthenticateDTO);
            return ret;
        } catch (IllegalArgumentException ex) {
            throw new IllegalRequestException("authentication error");
        }
    }
}
