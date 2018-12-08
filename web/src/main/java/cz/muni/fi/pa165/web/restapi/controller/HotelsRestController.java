package cz.muni.fi.pa165.web.restapi.controller;

import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.facade.HotelFacade;
import cz.muni.fi.pa165.web.restapi.exception.ResourceNotFoundException;
import cz.muni.fi.pa165.web.restapi.exception.RestApiException;
import cz.muni.fi.pa165.web.restapi.exception.ServerProblemException;
import cz.muni.fi.pa165.web.restapi.hateoas.HotelResource;
import cz.muni.fi.pa165.web.restapi.hateoas.HotelResourceAssembler;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Miloslav Zezulka
 */
@RestController
@ExposesResourceFor(HotelDTO.class)
@RequestMapping("/hotels")
public class HotelsRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(HotelsRestController.class);

    private HotelFacade facade;
    private HotelResourceAssembler resourceAssembler;

    public HotelsRestController(@Autowired HotelFacade productFacade,
            @Autowired HotelResourceAssembler productResourceAssembler) {
        this.facade = productFacade;
        this.resourceAssembler = productResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<HotelResource>> getProducts() {
        LOGGER.debug("[REST] getProducts()");
        List<HotelResource> resourceCollection = resourceAssembler.toResources(facade.findAll());
        Resources<HotelResource> hotels = new Resources<>(resourceCollection,
                linkTo(HotelsRestController.class).withSelfRel(),
                linkTo(HotelsRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(hotels, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final HttpEntity<HotelResource> getProduct(@PathVariable("id") long id)
            throws ResourceNotFoundException {
        LOGGER.debug("[REST] getProduct({})", id);
        HotelDTO productDTO = facade.findById(id);
        if (productDTO == null) {
            throw new ResourceNotFoundException("product " + id + " not found");
        }
        HotelResource resource = resourceAssembler.toResource(productDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) throws RestApiException {
        LOGGER.debug("[REST] delete({})", id);
        try {
            facade.delete(id);
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
}
