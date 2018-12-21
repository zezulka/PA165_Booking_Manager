package cz.muni.fi.pa165.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Miloslav Zezulka
 */
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private BigDecimal total;

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private Room room;

    public Booking() {
    }

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

    public void setFromDate(LocalDate from) {
        this.fromDate = from;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate to) {
        this.toDate = to;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (fromDate != null ? fromDate.hashCode() : 0);
        hash = 53 * hash + (toDate != null ? toDate.hashCode() : 0);
        hash = 53 * hash + (user != null ? user.hashCode() : 0);
        hash = 53 * hash + (room != null ? room.hashCode() : 0);
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
        if (!(obj instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) obj;
        if (fromDate == null || !fromDate.equals(other.getFromDate())) {
            return false;
        }
        if (toDate == null || !toDate.equals(other.getToDate())) {
            return false;
        }
        if (user == null || !user.equals(other.getUser())) {
            return false;
        }
        return !(room == null || !room.equals(other.getRoom()));
    }
}
