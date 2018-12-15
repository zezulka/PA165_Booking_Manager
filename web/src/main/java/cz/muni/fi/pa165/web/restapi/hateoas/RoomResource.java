package cz.muni.fi.pa165.web.restapi.hateoas;

import java.math.BigDecimal;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import cz.muni.fi.pa165.api.dto.HotelDTO;
import cz.muni.fi.pa165.api.dto.RoomDTO;
import cz.muni.fi.pa165.enums.RoomType;

/**
 * 
 * @author Soňa Barteková
 *
 */
@Relation(value = "room", collectionRelation = "roomss")
@JsonPropertyOrder({"id", "number", "type", "description", "recommendedPrice"})
public class RoomResource extends ResourceSupport{
	

	@JsonProperty("id")
    private long dtoId;

    private HotelDTO hotel;

    private Integer number;

    private String description;

    private BigDecimal recommendedPrice;

    private byte[] image;

    private RoomType type;
    
    public RoomResource(RoomDTO room) {
    	dtoId = room.getId();
    	hotel = room.getHotel();
    	number = room.getNumber();
    	description = room.getDescription();
    	recommendedPrice = room.getRecommendedPrice();
    	image = room.getImage();
    	type = room.getType();
    }

	public Long getDtoId() {
		return dtoId;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public Integer getNumber() {
		return number;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getRecommendedPrice() {
		return recommendedPrice;
	}

	public byte[] getImage() {
		return image;
	}

	public RoomType getType() {
		return type;
	}

    
}
