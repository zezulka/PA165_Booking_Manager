package cz.muni.fi.pa165.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Miloslav Zezulka
 */
public class BookingDTO {

    private Long id;

    private BigDecimal total;

    private LocalDate fromDate;

    private LocalDate toDate;

    private UserDTO usr;

    private RoomDTO room;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

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

    public UserDTO getUser() {
        return usr;
    }

    public void setUser(UserDTO usr) {
        this.usr = usr;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + fromDate.hashCode();
        hash = 97 * hash + toDate.hashCode();
        hash = 97 * hash + usr.hashCode();
        hash = 97 * hash + room.hashCode();
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
        if (!(obj instanceof BookingDTO)) {
            return false;
        }
        final BookingDTO other = (BookingDTO) obj;
        if (!fromDate.equals(other.fromDate)) {
            return false;
        }
        if (!toDate.equals(other.toDate)) {
            return false;
        }
        if (!usr.equals(other.usr)) {
            return false;
        }
        return room.equals(other.room);
    }
}
