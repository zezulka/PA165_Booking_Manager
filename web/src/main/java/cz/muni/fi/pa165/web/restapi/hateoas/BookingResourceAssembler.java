package cz.muni.fi.pa165.web.restapi.hateoas;

import cz.muni.fi.pa165.api.dto.BookingDTO;
import cz.muni.fi.pa165.web.restapi.controller.BookingsRestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Soňa Barteková
 *
 */
@Component
public class BookingResourceAssembler extends ResourceAssemblerSupport<BookingDTO, BookingResource>{

    private EntityLinks entityLinks;
    private final static Logger LOGGER = LoggerFactory.getLogger(BookingResourceAssembler.class);
    
	public BookingResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
            @Autowired EntityLinks entityLinks) {
		super(BookingsRestController.class, BookingResource.class);
        this.entityLinks = entityLinks;
	}

	@Override
	public BookingResource toResource(BookingDTO entity) {
        long id = entity.getId();
        BookingResource resource = new BookingResource(entity);
        try {
            Link catLink = entityLinks.linkForSingleResource(BookingDTO.class, id).withSelfRel();
            resource.add(catLink);

            Link productsLink = entityLinks.linkForSingleResource(BookingDTO.class, id).slash("/bookings").withRel("bookings");
            resource.add(productsLink);

        } catch (Exception ex) {
            LOGGER.error("cannot link HATEOAS", ex);
        }
        return resource;
	}

}
