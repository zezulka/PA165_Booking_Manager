package cz.muni.fi.pa165.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
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
    private Customer customer;

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

    public LocalDate getFrom() {
        return fromDate;
    }

    public void setFrom(LocalDate from) {
        this.fromDate = from;
    }

    public LocalDate getTo() {
        return toDate;
    }

    public void setTo(LocalDate to) {
        this.toDate = to;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        hash = 53 * hash + (customer != null ? customer.hashCode() : 0);
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
        if (fromDate == null || !fromDate.equals(other.getFrom())) {
            return false;
        }
        if (toDate == null || !toDate.equals(other.getTo())) {
            return false;
        }
        if(customer == null || !customer.equals(other.getCustomer())) {
            return false;
        }
        return !(room == null || !room.equals(other.getRoom()));
    }  
}
