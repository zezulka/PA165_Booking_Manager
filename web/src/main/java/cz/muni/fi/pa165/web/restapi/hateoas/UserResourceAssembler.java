package cz.muni.fi.pa165.web.restapi.hateoas;

import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.web.restapi.controller.UsersRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Petr Valenta
 */
@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<UserDTO, UserResource> {

    private EntityLinks entityLinks;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserResourceAssembler.class);

    public UserResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired EntityLinks entityLinks) {
        super(UsersRestController.class, UserResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public UserResource toResource(UserDTO dto) {
        long id = dto.getId();
        UserResource resource = new UserResource(dto);
        try {
            Link catLink = entityLinks.linkForSingleResource(UserDTO.class, id).withSelfRel();
            resource.add(catLink);

            Link productsLink = linkTo(UsersRestController.class).withRel("users");
            resource.add(productsLink);

        } catch (Exception ex) {
            LOGGER.error("cannot link HATEOAS", ex);
        }
        return resource;
    }
}