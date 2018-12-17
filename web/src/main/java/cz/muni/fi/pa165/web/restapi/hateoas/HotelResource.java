package cz.muni.fi.pa165.web.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.dto.RoomDTO;
import java.util.List;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 * @author Miloslav Zezulka
 */
@Relation(value = "hotel", collectionRelation = "hotels")
@JsonPropertyOrder({"id", "name", "address"})
public class HotelResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String name;
    private String address;
    private List<RoomDTO> rooms;
    
    public HotelResource(HotelDTO hotel) {
        address = hotel.getAddress();
        name = hotel.getName();
        dtoId = hotel.getId();
        rooms = hotel.getRooms();
    }

    public String getAddress() {
        return address;
    }

    public long getDtoId() {
        return dtoId;
    }

    public String getName() {
        return name;
    }

    public List<RoomDTO> getRooms() {
        return rooms;
    }
    
    
}
