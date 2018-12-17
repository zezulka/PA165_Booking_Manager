package cz.muni.fi.pa165.web.restapi.hateoas;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import cz.muni.fi.pa165.api.dto.BookingDTO;
import cz.muni.fi.pa165.api.dto.RoomDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;


/**
 * 
 * @author Soňa Barteková
 *
 */
@Relation(value = "booking", collectionRelation = "bookings")
@JsonPropertyOrder({"id", "totalPrice", "fromDate", "toDate"})
public class BookingResource extends ResourceSupport{
	
	@JsonProperty("id")
    private long dtoId;

    private BigDecimal total;

    private LocalDate fromDate;

    private LocalDate toDate;

    private UserDTO usr;

    private RoomDTO room;
    
    public BookingResource(BookingDTO booking) {
    	dtoId = booking.getId();
    	total = booking.getTotal();
    	fromDate = booking.getFromDate();
    	toDate = booking.getToDate();
    	usr = booking.getUser();
    	room = booking.getRoom();
    }

	public long getDtoId() {
		return dtoId;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public UserDTO getUsr() {
		return usr;
	}

	public RoomDTO getRoom() {
		return room;
	}

}
