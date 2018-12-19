package cz.muni.fi.pa165.web.restapi.hateoas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.pa165.api.dto.RoomDTO;
import cz.muni.fi.pa165.web.restapi.controller.RoomsRestController;

/**
 * 
 * @author Soňa Barteková
 *
 */
@Component
public class RoomResourceAssembler extends ResourceAssemblerSupport<RoomDTO, RoomResource>{

    private EntityLinks entityLinks;
    private final static Logger LOGGER = LoggerFactory.getLogger(RoomResourceAssembler.class);
    
	public RoomResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired EntityLinks entityLinks) {
		super(RoomsRestController.class, RoomResource.class);
        this.entityLinks = entityLinks;
	}

	@Override
	public RoomResource toResource(RoomDTO dto) {
        long id = dto.getId();
        RoomResource resource = new RoomResource(dto);
        try {
            resource.add(entityLinks.linkForSingleResource(RoomDTO.class, id).withSelfRel());
            resource.add(entityLinks.linkForSingleResource(RoomDTO.class, id).slash("/rooms").withRel("rooms"));
            
            Method productImage = RoomsRestController.class.getMethod("roomImage", long.class, HttpServletRequest.class, HttpServletResponse.class);
            Link imageLink = linkTo(productImage.getDeclaringClass(), productImage, id).withRel("image");
            resource.add(imageLink);
            
        } catch (Exception ex) {
            LOGGER.error("cannot link HATEOAS", ex);
        }
        return resource;
	}

}
