package cz.muni.fi.pa165.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Miloslav Zezulka
 */
public class BookingCreateDTO {

    private BigDecimal total;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fromDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate toDate;

    private UserDTO usr;

    private RoomDTO room;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public UserDTO getUsr() {
        return usr;
    }

    public void setUsr(UserDTO usr) {
        this.usr = usr;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + fromDate.hashCode();
        hash = 53 * hash + toDate.hashCode();
        hash = 53 * hash + usr.hashCode();
        hash = 53 * hash + room.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BookingCreateDTO)) {
            return false;
        }
        final BookingCreateDTO other = (BookingCreateDTO) obj;
        if (fromDate == null || !fromDate.equals(other.getFromDate())) {
            return false;
        }
        if (toDate == null || !toDate.equals(other.getToDate())) {
            return false;
        }
        if (usr == null || !usr.equals(other.getUsr())) {
            return false;
        }
        return room != null && !room.equals(other.getRoom());
    }

    @Override
    public String toString() {
        return "BookingCreateDTO{" + "total=" + total + ", fromDate=" + fromDate + ", toDate=" + toDate + ", usr=" + usr + ", room=" + room + '}';
    }

}
