package cz.muni.fi.pa165.web.restapi.hateoas;

import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.web.restapi.controller.HotelsRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Miloslav Zezulka
 */
@Component
public class HotelResourceAssembler extends ResourceAssemblerSupport<HotelDTO, HotelResource> {

    private EntityLinks entityLinks;
    private final static Logger LOGGER = LoggerFactory.getLogger(HotelResourceAssembler.class);

    public HotelResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
            @Autowired EntityLinks entityLinks) {
        super(HotelsRestController.class, HotelResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public HotelResource toResource(HotelDTO dto) {
        long id = dto.getId();
        HotelResource resource = new HotelResource(dto);
        try {
            Link catLink = entityLinks.linkForSingleResource(HotelDTO.class, id).withSelfRel();
            resource.add(catLink);

            Link productsLink = entityLinks.linkForSingleResource(HotelDTO.class, id).slash("/hotels").withRel("hotels");
            resource.add(productsLink);

        } catch (Exception ex) {
            LOGGER.error("cannot link HATEOAS", ex);
        }
        return resource;
    }
}
